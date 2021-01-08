package com.qr.dataobject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.PageParams;
import com.qr.dataobject.entity.DataNamesFlow;
import com.qr.dataobject.service.IDataNamesFlowService;
import com.qr.result.ResultInfo;
import com.qr.utils.PageUtils;
import com.qr.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.qr.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/dataObject/dataNamesFlow")
@Api(value = "数据对象流向控制层",tags = "数据对象流向")
public class DataNamesFlowController extends BaseController {

	@Autowired
	private IDataNamesFlowService dataNamesFlowService;

	@ApiOperation(value="查询数据对象流向信息列表", notes="数据对象")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "username", value = "用户名", required = true ,dataType = "string"),
//			@ApiImplicitParam(name = "passwd", value = "密码", required = true ,dataType = "string")
	})
	@PostMapping(value = "/queryList")
	public ResultInfo<PageUtils> queryList(@RequestBody PageParams pageParams, HttpServletRequest request){
		Page<DataNamesFlow> page = new Query<DataNamesFlow>(pageParams).getPage();

		BaseMapper<DataNamesFlow> baseMapper = dataNamesFlowService.getBaseMapper();
		page = baseMapper.selectPage(page, null);

		return trueResult(new PageUtils(page), request);
	}

	@ApiOperation(value="新增数据对象流向中间信息", notes="数据对象")
	@PostMapping(value = "/save")
	public ResultInfo<Map> save(@Validated @RequestBody @NotNull List<DataNamesFlow> dataNamesFlows, HttpServletRequest request){
		boolean result = dataNamesFlowService.saveInfo(dataNamesFlows);
		if (result) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

	@ApiOperation(value = "删除数据对象流向信息",notes = "数据对象")
	@PostMapping(value = "/delete")
	public ResultInfo<String> delete(@NotNull @RequestBody List<Long> ids, HttpServletRequest request){

		boolean save = dataNamesFlowService.delInfo(ids);
		if (save) {
			return trueResult(request);
		}else {
			return falseResult(request);
		}
	}

}
