package com.qr.dataobject.service.impl;

import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.mapper.DataFlowInfoMapper;
import com.qr.dataobject.service.ICoreTableService;
import com.qr.dataobject.service.IDataFlowInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qr.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 数据流向信息表 服务实现类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Service
public class DataFlowInfoServiceImpl extends ServiceImpl<DataFlowInfoMapper, DataFlowInfo> implements IDataFlowInfoService {

	@Autowired
	private ICoreTableService coreTableService;

	@Override
	@Transactional(rollbackFor = RRException.class)
	public boolean saveInfo(DataFlowInfo dataFlowInfo) {
		//保存数据流向信息
		boolean save = save(dataFlowInfo);
		return save;
	}
}
