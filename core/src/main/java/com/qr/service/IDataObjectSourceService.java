package com.qr.service;

import com.qr.entity.DataInfoMsg;
import com.qr.entity.QueueMsg;

import java.util.List;

/**
 *  数据对象service
 * @Author wd
 * @Description //TODO
 * @Date 15:27 2020/9/7
 **/
public interface IDataObjectSourceService {

	/**
	 *  数据对象保存
	 * @Author wd
	 * @Description //TODO
	 * @Date 15:28 2020/9/7
	 **/
	Boolean saveSource(QueueMsg queueMsg);

	/**
	 *  数据信息入库保存
	 * @Author wd
	 * @Description //TODO
	 * @Date 16:29 2020/9/14
	 **/
	Boolean sqlWarehousing(List<DataInfoMsg> dataInfoMsgs);

}
