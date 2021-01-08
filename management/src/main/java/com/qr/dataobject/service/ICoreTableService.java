package com.qr.dataobject.service;

import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.param.DataFieldsParams;

import java.util.List;
import java.util.Map;

/**
 *  数据核心建表流程
 * @Author wd
 * @Description //TODO
 * @Date 10:46 2020/9/3
 **/
public interface ICoreTableService {

	/**
	 *  创建核心表
	 * @Author wd
	 * @Description //TODO
	 * @Date 10:48 2020/9/3
	 * @param dataName
	 * @param dataCode
	 * @param dataFlowInfoList
	 * @return boolean
	 **/
	boolean createTable(String dataName,String dataCode, List<DataFlowInfo> dataFlowInfoList);

	/**
	 * 修改数据字段
	 * @param dataFieldsParamsList
	 * @return
	 */
	Map<String,Object> modifyTableField(String dataName,List<DataFieldsParams> dataFieldsParamsList,List<DataFlowInfo> dataFlowInfoList);

	/*
	 * 删除表
	 * @param dataName
	 * @param dataFlowInfo
	 * @return
	 */
    boolean deleteTable(String dataName,DataFlowInfo dataFlowInfo);
}
