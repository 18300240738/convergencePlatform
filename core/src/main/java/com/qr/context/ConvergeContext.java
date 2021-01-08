package com.qr.context;

import com.qr.dao.IDataObjectDao;
import com.zaxxer.hikari.HikariDataSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 *  系统上下文信息
 * @Author wd
 * @Date 11:47 2020/9/7
 **/
public class ConvergeContext {

	private ConvergeContext() {
	}

	//数据对象队列
	public static final int QUEUESIZE = 5000;
	//数据入库队列
	public static final int SQLDATASAVEQUEUESIZE = 500;

	//数据流向信息
	private static ConcurrentMap<String, HikariDataSource> sysDataSourceMap = new ConcurrentHashMap<>();
	//数据保存对象实体信息
	private static Map<String, IDataObjectDao> sysDataObjectDaoMap = new ConcurrentHashMap<>();
	//数据对象队列
	private static BlockingQueue<String> dataObjectQueue = new ArrayBlockingQueue<>(QUEUESIZE);
	//数据入库队列
	private static BlockingQueue<String> sqlDataSaveQueue = new ArrayBlockingQueue<>(SQLDATASAVEQUEUESIZE);
	//自定义规则对象存储类
//	private static ConcurrentMap<String, CustomizeRule> customizeRuleMap = new ConcurrentHashMap<>()
	//第三方注册bean保存类
	private static ConcurrentMap<String, List<String>> thirdRegisteredMap = new ConcurrentHashMap<>();

	public static ConcurrentMap<String, HikariDataSource> getSysDataSourceMap() {
		return sysDataSourceMap;
	}

	public static void setSysDataSourceMap(ConcurrentMap<String, HikariDataSource> sysDataSourceMap) {
		ConvergeContext.sysDataSourceMap = sysDataSourceMap;
	}

	public static Map<String, IDataObjectDao> getSysDataObjectDaoMap() {
		return sysDataObjectDaoMap;
	}

	public static void setSysDataObjectDaoMap(Map<String, IDataObjectDao> sysDataObjectDaoMap) {
		ConvergeContext.sysDataObjectDaoMap = sysDataObjectDaoMap;
	}

	public static BlockingQueue<String> getDataObjectQueue() {
		return dataObjectQueue;
	}

	public static void setDataObjectQueue(BlockingQueue<String> dataObjectQueue) {
		ConvergeContext.dataObjectQueue = dataObjectQueue;
	}

	public static BlockingQueue<String> getSqlDataSaveQueue() {
		return sqlDataSaveQueue;
	}

	public static void setSqlDataSaveQueue(BlockingQueue<String> sqlDataSaveQueue) {
		ConvergeContext.sqlDataSaveQueue = sqlDataSaveQueue;
	}

	public static ConcurrentMap<String, List<String>> getThirdRegisteredMap() {
		return thirdRegisteredMap;
	}

	public static void setThirdRegisteredMap(ConcurrentMap<String, List<String>> thirdRegisteredMap) {
		ConvergeContext.thirdRegisteredMap = thirdRegisteredMap;
	}
}
