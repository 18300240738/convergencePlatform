package com.qr.dataobject.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.BaseController;
import com.qr.base.PageParams;
import com.qr.dataobject.param.DataFieldsParams;
import com.qr.dataobject.service.IDataFieldsService;
import com.qr.result.ResultInfo;
import com.qr.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据对象属性表 前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/dataObject/dataFields")
@Api(value = "数据对象属性控制层",tags = "数据对象属性")
public class DataFieldsController extends BaseController {

	@Autowired
	private IDataFieldsService dataFieldsService;

	@ApiOperation(value="查询数据对象属性列表", notes="数据对象")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
//			@ApiImplicitParam(name = "passwd", value = "密码", required = true ,dataType = "string")
	})
	@PostMapping(value = "/queryList")
	public ResultInfo<PageUtils> queryList(@RequestBody PageParams pageParams, HttpServletRequest request){

		Page<Object> page = dataFieldsService.queryPages(pageParams);
		return trueResult(new PageUtils(page),request);
	}

	@ApiOperation(value = "新增数据对象属性信息",notes = "数据对象")
	@PostMapping(value = "/add")
	public ResultInfo<String> add(@Validated @RequestBody List<DataFieldsParams> dataFieldsParams, HttpServletRequest request){
		boolean save = dataFieldsService.add(dataFieldsParams);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "编辑数据对象属性信息",notes = "数据对象")
	@PostMapping(value = "/modify")
	public ResultInfo modify(@NotNull @RequestBody List<DataFieldsParams> dataFieldsParams,HttpServletRequest request){
		Boolean save = dataFieldsService.modify(dataFieldsParams);
		if (save){
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	/**
	@ApiOperation(value = "删除数据对象属性信息",notes = "数据对象")
	@PostMapping(value = "/delete")
	public ResultInfo delete(@NotNull @RequestBody List<Long> ids,HttpServletRequest request){

		Map<String,Object> result = dataFieldsService.delete(ids);
		return trueResult(result,request);
	}*/
}
