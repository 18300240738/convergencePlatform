package com.qr.service;

import com.qr.entity.QueueMsg;

/**
 *  数据汇聚入队service
 * @Author wd
 * @Description //TODO
 * @Date 15:09 2020/9/7
 **/
public interface IDataObjectQueueService {

	/**
	 *  数据加入队列
	 * @Author wd
	 * @Description //TODO
	 * @Date 15:26 2020/9/7
	 **/
	Boolean addQueue(QueueMsg msg);
}
