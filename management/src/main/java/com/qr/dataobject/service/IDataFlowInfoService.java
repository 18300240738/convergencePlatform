package com.qr.dataobject.service;

import com.qr.dataobject.entity.DataFlowInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据流向信息表 服务类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
public interface IDataFlowInfoService extends IService<DataFlowInfo> {

	/**
	 *  保存数据流向数据
	 * @Author wd
	 * @Description //TODO
	 * @Date 10:43 2020/9/3
	 * @param dataFlowInfo
	 * @return boolean
	 **/
	boolean saveInfo(DataFlowInfo dataFlowInfo);
}
