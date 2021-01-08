package com.qr.controller;

import com.alibaba.fastjson.JSON;
import com.qr.domain.ResultCode;
import com.qr.encryption.SM4Utils;
import com.qr.entity.QueueMsg;
import com.qr.log.LogHandler;
import com.qr.result.ResultInfo;
import com.qr.service.IDataObjectQueueService;
import com.qr.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  核心服务入参
 * @Author wd
 * @since 18:11 2020/9/21
 **/
@RestController
@RequestMapping("/converge/")
public class DataObjectController {

	@Autowired
	private IDataObjectQueueService dataObjectQueueService;

	@RequestMapping("/dataObject/save")
	public ResultInfo<String> save(@RequestParam(required = true) String data, HttpServletRequest request){
		data = (String) request.getAttribute("data");
		QueueMsg queueMsg ;
		try {
			queueMsg = JSON.parseObject(data, QueueMsg.class);
		}catch (Exception e){
			return ResultInfo.setR(ResultCode.VALIDATE_FAILED.getCode(),ResultCode.VALIDATE_FAILED.getMessage(),String.valueOf(request.getAttribute("requestId")),"");
		}

		Boolean aBoolean = dataObjectQueueService.addQueue(queueMsg);
		//日志接口处理类
		Map<String, LogHandler> logHandlerMap = SpringContextUtil.getApplicationContext().getBeansOfType(LogHandler.class);
		logHandlerMap.forEach((k,v) ->{
			v.queueBeforeHandle(queueMsg,aBoolean);
		});

		if (Boolean.TRUE.equals(aBoolean)) {
			return ResultInfo.setTrueObject(String.valueOf(request.getAttribute("requestId")));
		}
		return ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")));
	}
}
