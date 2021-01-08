package com.qr.context;

import com.qr.dao.IDataObjectDao;
import com.qr.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 *  数据对象DAO层上下文信息
 * @Author wd
 * @Description //TODO
 * @Date 17:17 2020/9/8
 **/
@Component
public class DataObjectDaoContext {

	@Autowired
	public void setDataObjectDaoMap(Map<String, IDataObjectDao> dataObjectDaoMap){
		dataObjectDaoMap.forEach((k,v) -> {
			ConvergeContext.getSysDataObjectDaoMap().put(k,v);
		});
	}

	public static IDataObjectDao getDataObjectDao(String type){
		for (Map.Entry<String, IDataObjectDao> entry : ConvergeContext.getSysDataObjectDaoMap().entrySet()) {
			String k = entry.getKey();
			IDataObjectDao v = entry.getValue();
			if (k.contains(type.toLowerCase())) {
				return v;
			}
		}
		throw new RRException("获取数据库保存类失败!");
	}
}
