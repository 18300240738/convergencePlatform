package com.qr.service.impl;

import com.alibaba.fastjson.JSON;
import com.qr.context.ConvergeContext;
import com.qr.entity.QueueMsg;
import com.qr.enums.RulePositionEnum;
import com.qr.exception.RRException;
import com.qr.service.IDataObjectCheckRuleService;
import com.qr.service.IDataObjectQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataObjectQueueServiceImpl implements IDataObjectQueueService {

	@Autowired
	private IDataObjectCheckRuleService dataObjectCheckRuleService;

	@Override
	public Boolean addQueue(QueueMsg msg) {
		//**************************** 数据前置规则处理 ****************************
		try {
			dataObjectCheckRuleService.checkDataFields(msg,RulePositionEnum.BEFORE);
		} catch (Exception e) {
			log.error("前置校验规则不通过!{}",e);
			throw new RRException(e.getMessage());
		}

		return ConvergeContext.getDataObjectQueue().add(JSON.toJSONString(msg));
	}
}
