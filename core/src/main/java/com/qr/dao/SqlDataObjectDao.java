package com.qr.dao;

import com.alibaba.fastjson.JSON;
import com.qr.context.ConvergeContext;
import com.qr.entity.DataFields;
import com.qr.entity.DataInfoMsg;
import com.qr.entity.QueueMsg;
import com.qr.enums.ConvergenceContentEnum;
import com.qr.listener.SqlDataSourceSaveListener;
import com.qr.utils.DbUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  sql入库保存
 * @Author wd
 * @since 18:24 2020/9/21
 **/

@Component
@Slf4j
public class SqlDataObjectDao implements IDataObjectDao {

	@Autowired
	private SqlDataSourceSaveListener sqlDataSourceSaveListener;

	@Override
	public Boolean saveData(QueueMsg queueMsg, String flowCode, List<DataFields> dataFieldsList) {
		//************ 构建sql ************
		StringBuilder createSql = new StringBuilder();
		createSql.append(" INSERT INTO ");
		createSql.append(" `"+queueMsg.getDataName()+"` ");

		Map<String, Object> dataFields = JSON.parseObject(queueMsg.getData(),Map.class);

		if (dataFields != null) {
			createSql.append(" ( ");

			StringBuilder key = new StringBuilder();
			StringBuilder value = new StringBuilder();
			dataFields.forEach((k, v) -> {
				dataFieldsList.forEach(fields -> {
					if (k.equals(fields.getFieldName())){
						key.append("`" + k + "` ,");
						value.append("'" + v + "' ,");
					}
				});
			});

			//数据异常原因保存
			if (queueMsg.getDataName().contains(ConvergenceContentEnum.err.name())){
				key.append("`" + ConvergenceContentEnum.errMsg.name() + "` ,");
				value.append("'" + dataFields.get(ConvergenceContentEnum.errMsg.name()) + "' ,");
			}

			createSql.append(key.substring(0,key.length() - 1));
			createSql.append(" ) VALUES (");
			createSql.append(value.substring(0,value.length() - 1));
			createSql.append(" )");
		}else {
			createSql.append(" ( ) VALUES ()");
		}

//		log.info("************ sql构建结果:{}",createSql )

		DataInfoMsg dataInfoMsg = new DataInfoMsg();
		dataInfoMsg.setFlowCode(flowCode);
		dataInfoMsg.setCreateSql(createSql.toString());
		try {
			ConvergeContext.getSqlDataSaveQueue().add(JSON.toJSONString(dataInfoMsg));
		} catch (Exception e) {
			log.error("队列满:{}",e.getMessage());
			//********************** 执行队列消费 **********************
			sqlDataSourceSaveListener.dealwith();
			//********************** 数据加入队列 **********************
			ConvergeContext.getSqlDataSaveQueue().add(JSON.toJSONString(dataInfoMsg));
		}
		return Boolean.TRUE;
	}

	/**
	 *  执行数据保存
	 * @Author wd
	 * @Date 17:03 2020/9/14
	 **/
	public Boolean sourceSave(HikariDataSource hikariDataSource, List<DataInfoMsg> dataInfoMsgs) throws SQLException {
		Connection connection = null;
		Statement preparedStatement = null;
		try {
			connection = hikariDataSource.getConnection();
			//********************** 开启事物 **********************
			DbUtil.beginTransaction(connection);
//			log.info("批量新增sql语句:{}",sql)
			preparedStatement = connection.createStatement();

			for (DataInfoMsg dataInfoMsg:dataInfoMsgs){
				preparedStatement.addBatch(dataInfoMsg.getCreateSql());
			}

			int[] resultSet = preparedStatement.executeBatch();
			//********************** 提交事物 **********************
			DbUtil.commitTransaction(connection);

			List<Integer> list = Arrays.stream(resultSet).boxed().collect(Collectors.toList());

			return !list.contains(0);
		} finally{
			if(preparedStatement != null){
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
