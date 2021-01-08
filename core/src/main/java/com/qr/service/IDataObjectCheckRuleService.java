package com.qr.service;

import com.alibaba.fastjson.JSONObject;
import com.qr.entity.FieldRule;
import com.qr.entity.QueueMsg;
import com.qr.entity.RuleInfos;
import com.qr.enums.RulePositionEnum;
import com.qr.enums.RuleTypeEnum;
import com.qr.exception.RRException;
import com.qr.utils.BasicVerifyUtils;
import com.qr.utils.RedisUtils;
import com.qr.utils.RegularVerifyUtils;
import com.qr.utils.TypeLimitValueVerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.qr.enums.ConvergenceContentEnum.FIELDRULES;
import static com.qr.enums.ConvergenceContentEnum.RULEINFOS;

/**
 *  规则校验实现类
 * @Author wd
 * @Description //TODO
 * @Date 14:13 2020/9/11
 **/
public interface IDataObjectCheckRuleService {

	/**
	 *  数据对象属性校验()
	 * @Author wd
	 * @Date 10:26 2020/9/10
	 **/
	void checkDataFields(QueueMsg queueMsg, RulePositionEnum rulePositionEnum);

}
