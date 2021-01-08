package com.qr.init;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qr.context.ConvergeContext;
import com.qr.domain.ResultCode;
import com.qr.entity.DataFlowInfo;
import com.qr.entity.DataNamesFlow;
import com.qr.result.ResultInfo;
import com.qr.utils.RedisUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.qr.context.ConvergeContext.*;
import static com.qr.enums.ConvergenceContentEnum.DATANAMESFLOWS;

/**
 *  获取数据源信息
 * @Author wd
 * @Description //TODO
 * @Date 14:40 2020/9/8
 **/
@Component
@Order(2)
@Slf4j
public class DataSourceRunner implements CommandLineRunner {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${request.serverAddress}")
	private String serverAddress;
	@Value("${request.dataSourcePath}")
	private String dataSourcePath;
	@Value("${data.mysql.driverClassName}")
	private String driverClassName;
	@Value("${data.mysql.maxLifetime}")
	private String maxLifetime;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void run(String... args) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>("{}",headers);

		ResponseEntity<String> response = restTemplate.postForEntity(serverAddress+dataSourcePath, request, String.class);
//		log.info("获取到数据源信息：{}",response)

		ResultInfo resultInfo = JSONObject.parseObject(response.getBody(), ResultInfo.class);
		if (response.getStatusCode().value() != ResultCode.SUCCESS.getCode() || resultInfo.getCode() != ResultCode.SUCCESS.getCode()){
			log.error("数据源信息获取异常!");
			System.exit(0);
		}else {
			//******************************* 数据源处理 *******************************
			JSONObject jsonObject = JSONObject.parseObject(String.valueOf(resultInfo.getData()));
			JSONArray flowInfos = jsonObject.getJSONArray("flowInfos");
			List<DataFlowInfo> dataFlowInfoList = flowInfos.toJavaList(DataFlowInfo.class);
			dataFlowInfoList.forEach(dataFlowInfo -> {
				//sql
				HikariConfig config = new HikariConfig();
				config.setJdbcUrl(dataFlowInfo.getDatabaseAddress());
				config.setDriverClassName(driverClassName);
				config.setUsername(dataFlowInfo.getDatabaseUsername());
				config.setPassword(dataFlowInfo.getDatabasePassword());
				config.addDataSourceProperty("cachePrepStmts", "true");
				config.addDataSourceProperty("prepStmtCacheSize", "250");
				config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
				config.setMaxLifetime(Long.parseLong(maxLifetime));
				config.setMaximumPoolSize(20);
				config.setPoolName(dataFlowInfo.getDataFlowName());

				ConvergeContext.getSysDataSourceMap().put(dataFlowInfo.getDataFlowCode(),new HikariDataSource(config));
			});

			//******************************* 数据对象对应数据源信息处理 *******************************
			JSONArray dataNamesFlows = jsonObject.getJSONArray("dataNamesFlows");
			List<DataNamesFlow> dataNamesFlowsList = dataNamesFlows.toJavaList(DataNamesFlow.class);
			Map dataNameFlowMap = dataNamesFlowsList.stream().collect(Collectors.groupingBy(DataNamesFlow::getDataName));

			RedisUtils.addMap(stringRedisTemplate,DATANAMESFLOWS.name(),dataNameFlowMap);

		}

		log.info("********************** 数据源信息加载至上下文成功 **********************");
	}
}
