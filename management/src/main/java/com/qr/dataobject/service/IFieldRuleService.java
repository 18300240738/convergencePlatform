package com.qr.dataobject.service;

import com.qr.dataobject.entity.FieldRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字段校验规则中间表 服务类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
public interface IFieldRuleService extends IService<FieldRule> {

	/**
	 *  查询数据字段校验
	 * @Author wd
	 * @Description //TODO
	 * @Date 17:39 2020/9/7
	 * @return map
	 **/
	List<Map> queryFieldRuleList();
}
