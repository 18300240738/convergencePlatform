package com.qr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qr.customize.CustomizeRule;
import com.qr.entity.FieldRule;
import com.qr.entity.QueueMsg;
import com.qr.entity.RuleInfos;
import com.qr.enums.RulePositionEnum;
import com.qr.enums.RuleTypeEnum;
import com.qr.exception.RRException;
import com.qr.service.IDataObjectCheckRuleService;
import com.qr.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.qr.enums.ConvergenceContentEnum.FIELDRULES;
import static com.qr.enums.ConvergenceContentEnum.RULEINFOS;

/**
 *  规则校验实现类
 * @Author wd
 * @Description
 * @Date 14:13 2020/9/11
 **/
@Service
@Slf4j
public class DataObjectCheckRuleServiceImpl implements IDataObjectCheckRuleService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void checkDataFields(QueueMsg queueMsg, RulePositionEnum rulePositionEnum){
		//******************************* 获取数据对象属性对应规则 *******************************
		List<FieldRule> fieldRuleList = JSON.parseArray(RedisUtils.getMap(stringRedisTemplate, FIELDRULES.name(), queueMsg.getDataName()), FieldRule.class);
		fieldRuleList = fieldRuleList.stream().filter(fieldRule -> fieldRule.getPosition() == rulePositionEnum.getValue()).collect(Collectors.toList());

		Map<String,Object> dataFieldMap = JSON.parseObject(queueMsg.getData(), Map.class);
		fieldRuleList.forEach(fieldRule -> {

			//******************************* 获取校验规则信息 *******************************
			RuleInfos ruleInfos = JSONObject.parseObject(RedisUtils.getMap(stringRedisTemplate, RULEINFOS.name(), fieldRule.getRuleCode()), RuleInfos.class);
			if (dataFieldMap == null){
				if (!verifyRule(fieldRule.getFiledName(),null,ruleInfos,fieldRule.getRuleValue1(),fieldRule.getRuleValue2())) {
					throw new RRException(fieldRule.getFiledName() + " 规则校验不通过!");
				}
			}else {
				Object value = dataFieldMap.get(fieldRule.getFiledName());
				if (value == null) {
					throw new RRException(fieldRule.getFiledName() + " 规则校验不通过!");
				}
				if (!verifyRule(fieldRule.getFiledName(),value,ruleInfos,fieldRule.getRuleValue1(),fieldRule.getRuleValue2())) {
					throw new RRException(fieldRule.getFiledName() + " 规则校验不通过!");
				}
			}
		});
	}

	/**
	 *  校验规则
	 * @Author wd
	 * @Date 10:54 2020/9/10
	 **/
	public Boolean verifyRule(String fieldName,Object value, RuleInfos ruleInfos,Object param1,Object param2){
		Boolean result = true;
		//log.info("规则对象:{}",ruleInfos)
		switch (RuleTypeEnum.getByValue(ruleInfos.getRuleType())){
			case BASIC:
				result = BasicVerifyUtils.excute(ruleInfos.getRuleKey(),value,param1,param2);
				break;
			case REGULAR:
				result = RegularVerifyUtils.excute(value,ruleInfos.getRuleCode());
				break;
			case TYPELIMITVALUE:
				result = TypeLimitValueVerifyUtils.excute(value,ruleInfos.getRuleDetails());
				break;
			case CUSTOMIZE:
				Map<String, CustomizeRule> customizeRuleMap = SpringContextUtil.getApplicationContext().getBeansOfType(CustomizeRule.class);
				for (Map.Entry<String,CustomizeRule> map:customizeRuleMap.entrySet()){
					result = map.getValue().verify();
					if (!result){
						break;
					}
				}

				break;
			default:
				break;
		}
		return result;
	}

}
