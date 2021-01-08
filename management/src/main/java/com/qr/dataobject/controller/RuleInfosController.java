package com.qr.dataobject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.BaseController;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.RuleInfos;
import com.qr.dataobject.service.IRuleInfosService;
import com.qr.result.ResultInfo;
import com.qr.utils.PageUtils;
import com.qr.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 校验规则表 前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/dataObject/ruleInfos")
@Api(value = "属性规则控制层",tags = "规则信息")
public class RuleInfosController extends BaseController {

	@Autowired
	private IRuleInfosService ruleInfosService;

	@ApiOperation(value="查询属性规则对应关系信息列表", notes="数据对象")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
//			@ApiImplicitParam(name = "passwd", value = "密码", required = true ,dataType = "string")
	})
	@PostMapping(value = "/queryList")
	public ResultInfo<PageUtils> queryList(@RequestBody PageParams pageParams, HttpServletRequest request){
		Page<RuleInfos> page = new Query<RuleInfos>(pageParams).getPage();

		BaseMapper<RuleInfos> baseMapper = ruleInfosService.getBaseMapper();
		page = baseMapper.selectPage(page, null);

		return trueResult(new PageUtils(page), request);
	}

	@ApiOperation(value="新增校验规则信息", notes="数据对象")
	@PostMapping(value = "/save")
	public ResultInfo save(@RequestBody RuleInfos ruleInfos,HttpServletRequest request){
		boolean save = ruleInfosService.save(ruleInfos);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "编辑校验规则信息",notes = "数据对象")
	@PostMapping(value = "/update")
	public ResultInfo update(@RequestBody RuleInfos ruleInfos, HttpServletRequest request){
		Assert.notNull(ruleInfos.getId(),"主键id不能为空!");
		boolean save = ruleInfosService.updateById(ruleInfos);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "删除校验规则信息",notes = "数据对象")
	@PostMapping(value = "/delete")
	public ResultInfo delete(@RequestBody List<Long> ids, HttpServletRequest request){
		boolean remove = ruleInfosService.removeByIds(ids);
		if (remove) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}
}
