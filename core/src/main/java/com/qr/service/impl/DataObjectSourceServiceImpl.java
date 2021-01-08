package com.qr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qr.context.ConvergeContext;
import com.qr.context.DataObjectDaoContext;
import com.qr.dao.IDataObjectDao;
import com.qr.dao.SqlDataObjectDao;
import com.qr.entity.DataFields;
import com.qr.entity.DataInfoMsg;
import com.qr.entity.DataNamesFlow;
import com.qr.entity.QueueMsg;
import com.qr.enums.ConvergenceContentEnum;
import com.qr.enums.DataSourceTypeEnum;
import com.qr.enums.RulePositionEnum;
import com.qr.log.LogHandler;
import com.qr.service.IDataObjectSourceService;
import com.qr.utils.RedisUtils;
import com.qr.utils.SpringContextUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static com.qr.enums.ConvergenceContentEnum.DATANAMESFLOWS;

/**
 *  数据对象service实现类
 * @Author wd
 * @Description //TODO
 * @Date 14:13 2020/9/11
 **/
@Service
@Slf4j
public class DataObjectSourceServiceImpl implements IDataObjectSourceService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private DataObjectCheckRuleServiceImpl dataObjectCheckRuleService;
	@Autowired
	private SqlDataObjectDao sqlDataObjectDao;

	@Override
	public Boolean saveSource(QueueMsg queueMsg) {
		//************ 获取数据源 ************
		List<DataNamesFlow> dataNamesFlowList = JSONObject.parseArray(RedisUtils.getMap(stringRedisTemplate, DATANAMESFLOWS.name(),queueMsg.getDataName()), DataNamesFlow.class);
//		"************ 获取对象数据属性 ************"
		String dataFieldStr = RedisUtils.get(stringRedisTemplate, queueMsg.getDataName());
		List<DataFields> dataFieldsList = JSON.parseArray(dataFieldStr, DataFields.class);

//		log.info("************ 执行数据对象规则校验 ************")
		boolean flag = true;
		try {
			dataObjectCheckRuleService.checkDataFields(queueMsg, RulePositionEnum.AFTER);
		} catch (Exception e) {
			//处理校验规则不通过 ---> 生成错误信息 保存
			log.info("{}",e.getMessage());
			String data = queueMsg.getData();
			JSONObject jsonObject = JSON.parseObject(data);
			jsonObject.put(ConvergenceContentEnum.errMsg.name(),e.getMessage());
			queueMsg.setData(jsonObject.toJSONString());
			queueMsg.setDataName(queueMsg.getDataName() + "_error");
			flag = false;
		}

		//数据校验日志记录
		Map<String, LogHandler> logHandlerMap = SpringContextUtil.getApplicationContext().getBeansOfType(LogHandler.class);
		boolean finalFlag = flag;
		logHandlerMap.forEach((k, v) -> {
			v.queueAfterHandle(queueMsg, finalFlag);
		});

		for (DataNamesFlow dataNamesFlow:dataNamesFlowList){
//			log.info("************ 执行具体数据源保存操作:{} ************",dataNamesFlow.getFlowName())
			IDataObjectDao dataObjectDao = DataObjectDaoContext.getDataObjectDao(DataSourceTypeEnum.getName(dataNamesFlow.getFlowType()));
			Boolean aBoolean = dataObjectDao.saveData(queueMsg, dataNamesFlow.getFlowCode(), dataFieldsList);
			if (!aBoolean) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean sqlWarehousing(List<DataInfoMsg> dataInfoMsgs) {
		Map<String, List<DataInfoMsg>> flowCodeMap = dataInfoMsgs.stream().collect(Collectors.groupingBy(DataInfoMsg::getFlowCode));
		AtomicReference<Boolean> flag = new AtomicReference<>(true);
		flowCodeMap.forEach((k,v) -> {
			//************ 获取数据源 ************
			HikariDataSource hikariDataSource = ConvergeContext.getSysDataSourceMap().get(k);

			try {
				sqlDataObjectDao.sourceSave(hikariDataSource,v);
				log.info("保存：{}",v.size());
			} catch (SQLException throwables) {
				log.error("执行批量保存失败!:{}",throwables);
				flag.set(false);
			}
		});

		return flag.get();
	}
}
