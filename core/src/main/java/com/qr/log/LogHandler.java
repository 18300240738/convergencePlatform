package com.qr.log;

import com.qr.entity.QueueMsg;

/**
 *  日志处理接口
 * @Author wd
 * @since 11:15 2020/9/27
 **/
public interface LogHandler {

	/**
	 * 接口调用次数
	 * @param flag
	 */
	void interfaceCount(Boolean flag);
	/**
	 * 入队之前处理 flag为处理标识
	 * @param queueMsg
	 * @param flag
	 */
	void queueBeforeHandle(QueueMsg queueMsg,boolean flag);

	/**
	 *  入队之后处理 flag为处理标识
	 * @param queueMsg
	 * @param flag
	 */
	void queueAfterHandle(QueueMsg queueMsg,boolean flag);
}
