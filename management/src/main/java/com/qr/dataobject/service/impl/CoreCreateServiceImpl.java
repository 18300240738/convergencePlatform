package com.qr.dataobject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.qr.config.Content;
import com.qr.dataobject.entity.DataFields;
import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.mapper.DataFieldsMapper;
import com.qr.dataobject.param.DataFieldsParams;
import com.qr.dataobject.service.ICoreTableService;
import com.qr.enums.ActionTypeEnum;
import com.qr.exception.RRException;
import com.qr.utils.DbUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 构建建表sql实现类
 * @Author wd
 * @since 18:08 2020/9/21
 **/
@Service
@Slf4j
public class CoreCreateServiceImpl implements ICoreTableService {

	@Autowired
	private DataFieldsMapper dataFieldsMapper;
	@Value("${data.mysql.driverClassName}")
	private String driverClassName;


	@Override
	public boolean createTable(String dataName,String dataCode, List<DataFlowInfo> dataFlowInfoList) {
		//构建创建数据表语句
		StringBuilder creatsql = new StringBuilder();
		StringBuilder creatErrsql = new StringBuilder();

		creatsql.append("CREATE TABLE ");
		creatErrsql.append("CREATE TABLE ");
		creatsql.append(dataName);
		creatErrsql.append(dataName + "_" + Content.ERR);

		List<DataFields> dataFieldsList = dataFieldsMapper.selectList(new LambdaQueryWrapper<DataFields>().eq(DataFields::getDataCode, dataCode));
		creatsql.append(" ( `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',");
		creatErrsql.append(" ( `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',");

		StringBuffer primaryKey = new StringBuffer();
		primaryKey.append("PRIMARY KEY ( `id` ");
		for (int i = 0; i < dataFieldsList.size(); i++){
			StringBuilder field = new StringBuilder();
			DataFields dataFields = dataFieldsList.get(i);
			field.append(dataFields.getFieldName() +" "+ dataFields.getFieldType());
			if (!"date".equalsIgnoreCase(dataFields.getFieldType())) {
				field.append("(" + dataFields.getFieldLength() + ")");
			}

			//异常数据sql构造!
			creatErrsql.append(field+",");

			if (dataFields.getIsNotNull() == 1){
				field.append(" not null ");
			}
			creatsql.append(field+",");

			if (dataFields.getIsKey() == 1){
				primaryKey.append(",`"+dataFields.getFieldName()+"`");
			}
		}
		creatErrsql.append("errMsg longtext COMMENT '数据异常原因', ");

		primaryKey.append(" )");
		creatsql.append(primaryKey);
		creatErrsql.append("PRIMARY KEY ( `id` )");

		creatsql.append(" ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;");
		creatErrsql.append(" ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;");

		log.info("校验通过数据 建表语句:{}",creatsql.toString());
		log.info("检验不通过数据 建表语句:{}",creatErrsql.toString());
		//********************* 执行 *********************
		for (DataFlowInfo dataFlowInfo:dataFlowInfoList) {
			try {
				sqlExcute(dataFlowInfo, creatsql.toString(), creatErrsql.toString());
			} catch (Exception throwables) {
				log.error("执行创建sql异常!{}", throwables);
				return false;
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> modifyTableField(String dataName,List<DataFieldsParams> dataFieldsParamsList,List<DataFlowInfo> dataFlowInfoList) {
		Map<String,Object> result = Maps.newHashMap();
		StringBuilder sql = new StringBuilder();

		//对操作分组
		Map<Integer, List<DataFieldsParams>> listMap = dataFieldsParamsList.stream().collect(Collectors.groupingBy(DataFieldsParams::getAction));
		listMap.forEach((k,v) -> {
			switch (ActionTypeEnum.getByValue(k)){
				case ADD:
					sql.append(addTableField(dataName,v));
					break;
				case DEL:
					sql.append(delTableField(dataName,v));
					break;
				case UPD:
					sql.append(updTableField(dataName,v));
					break;
				default:
					break;
			}
		});

		//执行sql
		dataFlowInfoList.forEach(dataFlowInfo -> {
			try {
				sqlExcute(dataFlowInfo,sql.toString(),null);
				result.put(dataFlowInfo.getDataFlowName(),"数据对象编辑成功!");
			} catch (Exception exception ) {
				throw new RRException("数据对象编辑失败!");
//				log.error("执行编辑sql异常!{}", exception)
//				result.put(dataFlowInfo.getDataFlowName(),"数据对象编辑失败!")
			}
		});
		return result;
	}

	@Override
	public boolean deleteTable(String dataName,DataFlowInfo dataFlowInfo) {
		StringBuilder deleteTable = new StringBuilder();
		deleteTable.append("DROP DATABASE ");
		deleteTable.append(dataName);

		try {
			sqlExcute(dataFlowInfo,deleteTable.toString(),"");
			return true;
		} catch (SQLException throwables) {
			return false;
		}
	}

	/**
	 * 	新增字段
	 * @param dataName
	 * @param dataFieldsParamsList
	 * @return
	 */
	public String addTableField(String dataName,List<DataFieldsParams> dataFieldsParamsList) {
		//********************* 构建新增字段语句 *********************
		StringBuilder addBuilder = new StringBuilder();
		StringBuilder primaryKey = new StringBuilder();
		primaryKey.append("PRIMARY KEY ( ");
		dataFieldsParamsList.forEach(dataFieldsParams -> {
			addBuilder.append("ALTER TABLE `"+ dataName + "` ADD `"+ dataFieldsParams.getFieldName() +"`"
					+ dataFieldsParams.getFieldType() + "(" + dataFieldsParams.getFieldLength() + ") ");
			if (dataFieldsParams.getIsKey() == 1){
				primaryKey.append("`"+dataFieldsParams.getFieldName()+"` , ");
			}
			if (dataFieldsParams.getIsNotNull() == 1){
				addBuilder.append(" NOT NULL");
			}
		});

		addBuilder.append(" ;");
		addBuilder.append(primaryKey);
		return addBuilder.toString();
	}

	/**
	 * 构建删除语句
	 * @param dataName
	 * @param dataFieldsParamsList
	 * @return
	 */
	public String delTableField(String dataName,List<DataFieldsParams> dataFieldsParamsList) {
		//********************* 构建删除语句 *********************
		StringBuilder deleteBuilder = new StringBuilder();
		dataFieldsParamsList.forEach(fieldName -> {
			deleteBuilder.append("ALTER TABLE `"+ dataName + "` DROP `"+ fieldName.getFieldName() +"`;");
		});
		return deleteBuilder.toString();
	}

	public String updTableField(String dataName,List<DataFieldsParams> dataFieldsParamsList) {
		//********************* 构建删除语句 *********************
		StringBuilder addBuilder = new StringBuilder();
		dataFieldsParamsList.forEach(dataFieldsParams -> {
			addBuilder.append("ALTER TABLE `"+ dataName + "` ADD `"+ dataFieldsParams.getFieldName() +"`"
					+ dataFieldsParams.getFieldType() + "(" + dataFieldsParams.getFieldLength() + ") ");

			if (dataFieldsParams.getIsNotNull() == 1){
				addBuilder.append(" NOT NULL");
			}
		});
		return addBuilder.toString();
	}

	/**
	 *  执行sql语句
	 * @Author wd
	 * @Date 14:14 2020/9/7
	 **/
	private void sqlExcute(DataFlowInfo dataFlowInfo,String sql,String errSql) throws SQLException {
		Connection connection = null;
		Statement statement = null;

		try {
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(dataFlowInfo.getDatabaseAddress(),dataFlowInfo.getDatabaseUsername(),dataFlowInfo.getDatabasePassword());
			DbUtil.beginTransaction(connection);
			statement = connection.createStatement();

			statement.addBatch(sql);
			if (StringUtils.isNoneBlank(errSql)) {
				statement.addBatch(errSql);
			}

			int[] resultSet = statement.executeBatch();
			List<Integer> list = Arrays.stream(resultSet).boxed().collect(Collectors.toList());
			if (!list.contains(0)){
				DbUtil.commitTransaction(connection);
			}
		} catch (Exception e){
			log.error("数据表格创建异常:{}",e);
			DbUtil.rollBackTransaction(connection);
			throw new RRException(e.getMessage());
		} finally {
			if(connection != null){
				connection.close();
			}
			if (statement != null){
				statement.close();
			}
		}
	}
}
