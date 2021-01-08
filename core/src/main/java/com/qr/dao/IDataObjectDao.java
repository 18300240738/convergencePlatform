package com.qr.dao;

import com.qr.entity.DataFields;
import com.qr.entity.QueueMsg;
import java.util.List;

/**
 *  数据对象dao层
 * @Author wd
 * @Description //TODO
 * @Date 16:24 2020/9/7
 **/
public interface IDataObjectDao {

	/**
	 *  数据对象保存至数据源
	 * @Author wd
	 * @Description //TODO
	 * @Date 9:54 2020/9/10
	 **/
	Boolean saveData(QueueMsg queueMsg, String flowCode, List<DataFields> dataFieldsList);
}
