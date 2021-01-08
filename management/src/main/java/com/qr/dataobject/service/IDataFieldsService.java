package com.qr.dataobject.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.DataFields;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qr.dataobject.param.DataFieldsParams;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据对象属性表 服务类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
public interface IDataFieldsService extends IService<DataFields> {

	/**
	 *  新增数据对象属性信息
	 * @Author wd
	 * @Date 13:48 2020/8/31
	 * @param dataFieldsParams
	 * @return boolean
	 **/
	boolean add(List<DataFieldsParams> dataFieldsParams);

	/**
	 *  编辑数据对象属性信息
	 * @Author wd
	 * @Date 14:04 2020/8/31
	 * @param dataFieldsParams
	 * @return boolean
	 **/
	Boolean modify(List<DataFieldsParams> dataFieldsParams);

	/**
	 *  查询分页列表数据
	 * @Author wd
	 * @Date 15:24 2020/8/31
	 * @param pageParams
	 * @return page
	 **/
	Page<Object> queryPages(PageParams pageParams);
}
