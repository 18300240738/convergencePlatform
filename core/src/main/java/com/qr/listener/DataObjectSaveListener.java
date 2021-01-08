package com.qr.listener;

import com.alibaba.fastjson.JSON;
import com.qr.context.ConvergeContext;
import com.qr.entity.QueueMsg;
import com.qr.service.IDataObjectSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  数据对象队列监听类
 * @Author wd
 * @Description //TODO
 * @Date 9:31 2020/9/10
 **/
@Slf4j
@Component
public class DataObjectSaveListener implements CommonListener {

	@Autowired
	private IDataObjectSourceService dataObjectSourceService;

	@Override
	public void run() {
		while (1 > 0) {
			try {
				String msg = ConvergeContext.getDataObjectQueue().take();
				log.info("数据对象 队列剩余数量：{}",(ConvergeContext.QUEUESIZE - ConvergeContext.getDataObjectQueue().remainingCapacity()));
				QueueMsg queueMsg = JSON.parseObject(msg, QueueMsg.class);
				//**************** 执行队列数据处理 ****************************
				Boolean aBoolean = dataObjectSourceService.saveSource(queueMsg);
				if (!aBoolean) {
					log.error("处理队列数据异常!");
				}
			} catch (Exception e) {
				log.error("处理数据对象业务 队列数据 异常:{}", e);
			}
		}
	}
}
