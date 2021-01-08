package com.qr.dataobject.service.impl;

import com.qr.dataobject.entity.FieldRule;
import com.qr.dataobject.mapper.FieldRuleMapper;
import com.qr.dataobject.service.IFieldRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字段校验规则中间表 服务实现类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Service
public class FieldRuleServiceImpl extends ServiceImpl<FieldRuleMapper, FieldRule> implements IFieldRuleService {

	@Override
	public List<Map> queryFieldRuleList() {
		return baseMapper.queryFieldRuleList();
	}
}
