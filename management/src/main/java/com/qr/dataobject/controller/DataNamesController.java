package com.qr.dataobject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.BaseController;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.DataNames;
import com.qr.dataobject.service.IDataNamesService;
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
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 数据对象名称表 前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/dataObject/dataNames")
@Api(value = "数据对象名称控制层",tags = "数据对象名称")
public class DataNamesController extends BaseController {

	@Autowired
	private IDataNamesService dataNamesService;

	@ApiOperation(value="查询数据对象名称列表", notes="数据对象")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
//			@ApiImplicitParam(name = "passwd", value = "密码", required = true ,dataType = "string")
	})
	@PostMapping(value = "/queryList")
	public ResultInfo<PageUtils> queryList(@RequestBody PageParams pageParams, HttpServletRequest request){
		Page<DataNames> page = new Query<DataNames>(pageParams).getPage();

		BaseMapper<DataNames> baseMapper = dataNamesService.getBaseMapper();
		page = baseMapper.selectPage(page, null);

		return trueResult(new PageUtils(page), request);
	}

	@ApiOperation(value="新增数据对象名称信息", notes="数据对象")
	@PostMapping(value = "/save")
	public ResultInfo save(@RequestBody DataNames dataNames,HttpServletRequest request){
		boolean save = dataNamesService.save(dataNames);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "编辑数据对象名称信息",notes = "数据对象")
	@PostMapping(value = "/update")
	public ResultInfo update(@RequestBody DataNames dataNames, HttpServletRequest request){
		Assert.notNull(dataNames.getId(),"主键id不能为空!");
		boolean save = dataNamesService.updateById(dataNames);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "删除数据对象名称信息",notes = "数据对象")
	@PostMapping(value = "/delete")
	public ResultInfo delete(@NotNull @RequestBody List<Long> ids, HttpServletRequest request){

		boolean save = dataNamesService.removeByIds(ids);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}
}
