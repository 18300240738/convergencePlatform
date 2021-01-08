package com.qr.dataobject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.BaseController;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.service.IDataFlowInfoService;
import com.qr.result.ResultInfo;
import com.qr.utils.PageUtils;
import com.qr.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 数据流向信息表 前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/dataObject/dataFlowInfo")
@Api(value = "流向信息控制层",tags = "流向信息")
public class DataFlowInfoController extends BaseController {

	@Autowired
	private IDataFlowInfoService dataFlowInfoService;

	@ApiOperation(value="查询流向信息列表", notes="数据对象")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
//			@ApiImplicitParam(name = "passwd", value = "密码", required = true ,dataType = "string")
	})
	@PostMapping(value = "/queryList")
	public ResultInfo<PageUtils> queryList(@RequestBody PageParams pageParams, HttpServletRequest request){
		Page<DataFlowInfo> page = new Query<DataFlowInfo>(pageParams).getPage();

		BaseMapper<DataFlowInfo> baseMapper = dataFlowInfoService.getBaseMapper();
		page = baseMapper.selectPage(page, null);

		return trueResult(new PageUtils(page), request);
	}

	@ApiOperation(value="新增流向信息", notes="数据对象")
	@PostMapping(value = "/save")
	public ResultInfo save(@Validated @RequestBody DataFlowInfo dataFlowInfo, HttpServletRequest request){
		boolean save = dataFlowInfoService.saveInfo(dataFlowInfo);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "编辑流向信息",notes = "数据对象")
	@PostMapping(value = "/update")
	public ResultInfo update(@RequestBody DataFlowInfo dataFlowInfo,HttpServletRequest request){

		Assert.notNull(dataFlowInfo.getId(),"主键id不能为空!");

		boolean save = dataFlowInfoService.updateById(dataFlowInfo);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "删除流向信息",notes = "数据对象")
	@PostMapping(value = "/delete")
	public ResultInfo delete(@NotNull @RequestBody List<Long> ids,HttpServletRequest request){
		boolean save = dataFlowInfoService.removeByIds(ids);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}
}
