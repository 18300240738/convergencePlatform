package com.qr.dataobject.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.DataFields;
import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.entity.FieldRule;
import com.qr.dataobject.mapper.DataFieldsMapper;
import com.qr.dataobject.mapper.DataFlowInfoMapper;
import com.qr.dataobject.param.DataFieldVO;
import com.qr.dataobject.param.DataFieldsParams;
import com.qr.dataobject.param.FieldRuleParams;
import com.qr.dataobject.param.FieldRuleVO;
import com.qr.dataobject.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qr.exception.RRException;
import com.qr.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据对象属性表 服务实现类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Service
public class DataFieldsServiceImpl extends ServiceImpl<DataFieldsMapper, DataFields> implements IDataFieldsService {

	@Autowired
	private IFieldRuleService fieldRuleService;
	@Autowired
	private ICoreTableService coreTableService;
	@Autowired
	private IDataNamesFlowService dataNamesFlowService;
	@Autowired
	private ICacheService cacheService;

	@Override
	@Transactional(rollbackFor = {RRException.class})
	public boolean add(List<DataFieldsParams> dataFieldsParamsList) {
		dataFieldsParamsList.forEach(dataFieldsParams ->{
			DataFields dataFields = JSONObject.parseObject(JSON.toJSONString(dataFieldsParams), DataFields.class);
			boolean save = save(dataFields);
			if (!save) {
				throw new RRException("新增数据对象属性失败!");
			}
			//新增数据对象属性校验规则
			@NotNull List<FieldRuleParams> fieldRuleParamsList = dataFieldsParams.getFieldRuleParams();
			if (fieldRuleParamsList != null && fieldRuleParamsList.size() > 0){
				List<FieldRule> fieldRuleList = JSONObject.parseArray(JSON.toJSONString(fieldRuleParamsList), FieldRule.class);
				fieldRuleList.forEach(fieldRule -> {
					fieldRule.setDataCode(dataFields.getDataCode());
					fieldRule.setDataName(dataFields.getDataName());
					fieldRule.setFieldCode(dataFields.getFieldCode());
					fieldRule.setFiledName(dataFields.getFieldName());
				});
				Boolean saveBatch = fieldRuleService.saveBatch(fieldRuleList);
				if (!save) {
					throw new RRException("新增数据对象属性失败!");
				}
			}
		});

		return true;
	}

	@Override
	@Transactional(rollbackFor = {RRException.class})
	public Boolean modify(List<DataFieldsParams> dataFieldsParamsList) {

		//查询数据源信息
		@NotNull  DataFieldsParams dataFieldsParams1 = dataFieldsParamsList.get(0);
		@NotNull String dataCode = dataFieldsParams1.getDataCode();
		@NotNull String dataName = dataFieldsParams1.getDataName();
		List<DataFlowInfo> dataFlowInfos = dataNamesFlowService.queryDataFlowInfo(dataCode);
		Map<String, Object> modifyTableField = coreTableService.modifyTableField(dataName,dataFieldsParamsList, dataFlowInfos);

		for (DataFieldsParams dataFieldsParams: dataFieldsParamsList) {
			DataFields dataFields = JSONObject.parseObject(JSON.toJSONString(dataFieldsParams), DataFields.class);

			Assert.notNull(dataFields.getId(), "主键id不能为空!");

			//处理数据对象属性校验规则
			boolean remove = fieldRuleService.remove(new LambdaQueryWrapper<FieldRule>().eq(FieldRule::getFieldCode, dataFields.getFieldCode()));
			if (!remove) {
				throw new RRException("编辑数据对象属性失败!");
			}
			List<FieldRuleParams> fieldRuleParamsList = dataFieldsParams.getFieldRuleParams();
			if (fieldRuleParamsList != null && fieldRuleParamsList.size() > 0) {
				List<FieldRule> fieldRuleList = JSONObject.parseArray(JSON.toJSONString(fieldRuleParamsList), FieldRule.class);
				fieldRuleList.forEach(fieldRule -> {
					fieldRule.setFieldCode(dataFields.getFieldCode());
					fieldRule.setFiledName(dataFields.getFieldName());
				});
				Boolean save = fieldRuleService.saveBatch(fieldRuleList);
				if (!save) {
					throw new RRException("编辑数据对象属性失败!");
				}
			}

			Boolean save = updateById(dataFields);
			if (!save) {
				throw new RRException("编辑数据对象属性失败!");
			}
		}

		cacheService.updateCache();
		return true;
	}

	@Override
	public Page queryPages(PageParams pageParams) {
		Page page = new Query<>(pageParams).getPage();

		page = this.page(page,null);
		List<DataFields> dataFieldsList = page.getRecords();
		List<String> fieldCodeList = dataFieldsList.stream().map(DataFields::getFieldCode).collect(Collectors.toList());

		List<FieldRule> fieldRuleList = fieldRuleService.list(new LambdaQueryWrapper<FieldRule>().in(FieldRule::getFieldCode,fieldCodeList));
		List<DataFieldVO> dataFieldVOList = Lists.newArrayList();
		dataFieldsList.forEach(dataFields -> {
			DataFieldVO dataFieldVO = JSONObject.parseObject(JSON.toJSONString(dataFields), DataFieldVO.class);
			List<FieldRule> fieldRuleCodeList = fieldRuleList.stream().filter(fieldRule -> dataFields.getFieldCode().equals(fieldRule.getFieldCode())).collect(Collectors.toList());
			List<FieldRuleVO> fieldRuleVOList = JSONObject.parseArray(JSON.toJSONString(fieldRuleCodeList), FieldRuleVO.class);
			dataFieldVO.setFieldRuleVos(fieldRuleVOList);
			dataFieldVOList.add(dataFieldVO);
		});
		page.setRecords(dataFieldVOList);
		return page;
	}
}
