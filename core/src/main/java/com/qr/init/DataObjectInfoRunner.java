package com.qr.init;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qr.domain.ResultCode;
import com.qr.entity.DataFields;
import com.qr.entity.DataNames;
import com.qr.entity.FieldRule;
import com.qr.entity.RuleInfos;
import com.qr.result.ResultInfo;
import com.qr.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.qr.enums.ConvergenceContentEnum.*;

/**
 *  数据对象信息保存
 * @Author wd
 * @Description //TODO
 * @Date 14:39 2020/9/8
 **/
@Component
@Order(1)
@Slf4j
public class DataObjectInfoRunner implements CommandLineRunner {


	@Value("${request.serverAddress}")
	private String serverAddress;
	@Value("${request.dataFieldRulePath}")
	private String dataFieldRulePath;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void run(String... args) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>("{}",headers);

		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(serverAddress+dataFieldRulePath, request, String.class);
		} catch (RestClientException e) {
			log.error("数据对象信息获取异常!{}",e);
			System.exit(0);
		}

//		log.info("获取到数据对象信息：{}",response)
		ResultInfo resultInfo = JSONObject.parseObject(response.getBody(), ResultInfo.class);
		if (response.getStatusCode().value() != ResultCode.SUCCESS.getCode() || resultInfo.getCode() != ResultCode.SUCCESS.getCode()){
			log.error("数据对象信息获取异常!{}",response);
			System.exit(0);
		}else {
			JSONObject jsonObject = JSONObject.parseObject(String.valueOf(resultInfo.getData()));
			//******************************* 数据对象名称处理 *******************************
			JSONArray dataNames = jsonObject.getJSONArray("dataNames");
			List<DataNames> dataNamesList = dataNames.toJavaList(DataNames.class);
			RedisUtils.add(stringRedisTemplate,DATANAMES.name(),dataNamesList);

			//******************************* 数据对象属性处理 *******************************
			JSONArray dataFields = jsonObject.getJSONArray("dataFields");
			List<DataFields> dataFieldsList = dataFields.toJavaList(DataFields.class);
			Map<String, List<DataFields>> dataFieldMap = dataFieldsList.stream().collect(Collectors.groupingBy(DataFields::getDataName));
			dataFieldMap.forEach((k,v)-> {
				RedisUtils.add(stringRedisTemplate,k,v);
			});

			//******************************* 数据对象属性规则处理 *******************************
			JSONArray fieldRules = jsonObject.getJSONArray("fieldRules");
			List<FieldRule> fieldRuleList = fieldRules.toJavaList(FieldRule.class);
			Map dataFiledRuleMap = fieldRuleList.stream().collect(Collectors.groupingBy(FieldRule::getDataName));

			RedisUtils.addMap(stringRedisTemplate, FIELDRULES.name(),dataFiledRuleMap);

			//******************************* 校验规则数据处理 *******************************
			List<RuleInfos> ruleInfosList = jsonObject.getJSONArray("ruleInfos").toJavaList(RuleInfos.class);
			Map<String,Object> ruleInfoMap = Maps.newHashMap();
			ruleInfosList.forEach(ruleInfos -> {
				ruleInfoMap.put(ruleInfos.getRuleCode(),ruleInfos);
			});
			RedisUtils.addMap(stringRedisTemplate, RULEINFOS.name(),ruleInfoMap);
		}
		log.info("********************** 数据对象信息写入缓存成功 **********************");
	}
}
