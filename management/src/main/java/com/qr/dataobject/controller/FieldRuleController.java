package com.qr.dataobject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.BaseController;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.FieldRule;
import com.qr.dataobject.service.IFieldRuleService;
import com.qr.result.ResultInfo;
import com.qr.utils.PageUtils;
import com.qr.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 字段校验规则中间表 前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/dataObject/fieldRule")
@Api(value = "属性规则对应关系控制层",tags = "数据对象属性规则")
public class FieldRuleController extends BaseController {

	@Autowired
	private IFieldRuleService fieldRuleService;

	@ApiOperation(value="查询属性规则对应关系信息列表", notes="数据对象")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
//			@ApiImplicitParam(name = "passwd", value = "密码", required = true ,dataType = "string")
	})
	@PostMapping(value = "/queryList")
	public ResultInfo<PageUtils> queryList(@RequestBody PageParams pageParams, HttpServletRequest requests){
		Page<FieldRule> page = new Query<FieldRule>(pageParams).getPage();

		BaseMapper<FieldRule> baseMapper = fieldRuleService.getBaseMapper();
		page = baseMapper.selectPage(page, null);

		return trueResult(new PageUtils(page), requests);
	}

	@ApiOperation(value="新增数据字段校验规则信息", notes="数据对象")
	@PostMapping(value = "/save")
	public ResultInfo save(@RequestBody List<FieldRule> fieldRuleList,HttpServletRequest request){
		boolean save = fieldRuleService.saveBatch(fieldRuleList);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "编辑数据字段校验规则信息",notes = "数据对象")
	@PostMapping(value = "/update")
	public ResultInfo update(@RequestBody List<FieldRule> fieldRuleList, HttpServletRequest request){

		boolean save = fieldRuleService.updateBatchById(fieldRuleList);
		if (save) {
			return trueResult(request);
		}
		return falseResult(request);
	}

	@ApiOperation(value = "删除数据字段校验规则信息",notes = "数据对象")
	@PostMapping(value = "/delete")
	public ResultInfo delete(@NotNull @RequestBody List<Long> ids, HttpServletRequest request){

		boolean remove = fieldRuleService.removeByIds(ids);
		if (remove) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}
}
