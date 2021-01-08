package com.qr.dataobject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.qr.dataobject.entity.DataFields;
import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.entity.DataNames;
import com.qr.dataobject.entity.DataNamesFlow;
import com.qr.dataobject.mapper.DataFieldsMapper;
import com.qr.dataobject.mapper.DataFlowInfoMapper;
import com.qr.dataobject.mapper.DataNamesFlowMapper;
import com.qr.dataobject.mapper.DataNamesMapper;
import com.qr.dataobject.service.ICacheService;
import com.qr.dataobject.service.ICoreTableService;
import com.qr.dataobject.service.IDataNamesFlowService;
import com.qr.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wd
 * @since 2020-09-07
 */
@Service
public class DataNamesFlowServiceImpl extends ServiceImpl<DataNamesFlowMapper, DataNamesFlow> implements IDataNamesFlowService {

	@Autowired
	private ICoreTableService coreTableService;
	@Autowired
	private DataFlowInfoMapper dataFlowInfoMapper;
	@Autowired
	private DataFieldsMapper dataFieldsMapper;
	@Autowired
	private DataNamesMapper dataNamesMapper;
	@Autowired
	private ICacheService cacheService;

	@Override
	@Transactional(rollbackFor = RRException.class)
	public boolean saveInfo(List<DataNamesFlow> dataNamesFlows) {

		boolean save = saveBatch(dataNamesFlows);
		List<String> dataFlowInfoCodeList = Lists.newArrayList();
		dataNamesFlows.forEach(dataNamesFlow -> {
			dataFlowInfoCodeList.add(dataNamesFlow.getFlowCode());
		});
		if (save){
			//获取所有数据对象流向信息
			List<DataFlowInfo> dataFlowInfoList = dataFlowInfoMapper.selectList(new LambdaQueryWrapper<DataFlowInfo>().eq(DataFlowInfo::getDataFlowType,1)
					.in(DataFlowInfo::getDataFlowCode, dataFlowInfoCodeList));
			//处理数据建表流程
			DataNamesFlow dataNamesFlow = dataNamesFlows.get(0);
			save = coreTableService.createTable(dataNamesFlow.getDataName(),dataNamesFlow.getDataCode(),dataFlowInfoList);
			if (!save){
				throw new RRException("创建数据对象失败!");
			}
			cacheService.updateCache();
		}
		return save;
	}

	@Override
	public List<DataFlowInfo> queryDataFlowInfo(String dataCode) {
		return baseMapper.queryDataFlowInfo(dataCode);
	}

	@Override
	public boolean delInfo(List<Long> ids) {
		for (Long id : ids){

			DataNamesFlow dataNamesFlow = baseMapper.selectById(id);
			//删除数据对象属性
			int delete = dataFieldsMapper.delete(new LambdaQueryWrapper<DataFields>().eq(DataFields::getDataCode, dataNamesFlow.getDataCode()));
			if (delete < 0){
				throw new RRException("删除数据对象信息异常!");
			}
			//删除数据对象名称
			delete = dataNamesMapper.delete(new LambdaQueryWrapper<DataNames>().eq(DataNames::getDataCode, dataNamesFlow.getDataCode()));
			if (delete < 0){
				throw new RRException("删除数据对象信息异常!");
			}
			//删除数据对象中间信息
			delete = baseMapper.deleteById(id);
			if (delete < 0){
				throw new RRException("删除数据对象信息异常!");
			}

			//处理表删除
			DataFlowInfo dataFlowInfo = dataFlowInfoMapper.selectOne(new LambdaQueryWrapper<DataFlowInfo>().eq(DataFlowInfo::getDataFlowCode, dataNamesFlow.getFlowCode()));
			boolean deleteTable = coreTableService.deleteTable(dataNamesFlow.getDataName(),dataFlowInfo);
			if (!deleteTable) {
				throw new RRException("删除数据表失败!");
			}
		}

		return true;
	}
}
