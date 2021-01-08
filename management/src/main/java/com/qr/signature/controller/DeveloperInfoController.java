package com.qr.signature.controller;


import com.qr.result.ResultInfo;
import com.qr.signature.param.DeveloperParams;
import com.qr.signature.service.IDeveloperInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.qr.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/signature/developerInfo")
@Api(value = "开发方登记信息",tags = "开发方登记信息")
public class DeveloperInfoController extends BaseController {

	@Autowired
	private IDeveloperInfoService developerInfoService;

	@PostMapping("/save")
	@ApiOperation(value="数据对象流向 名称 属性信息", notes="核心层交互")
	public ResultInfo save(@Valid @RequestBody DeveloperParams developerParams, HttpServletRequest request){

		Boolean aBoolean = developerInfoService.save(developerParams);
		if (aBoolean) {
			return ResultInfo.setTrueObject(getRequestId(request));
		}else {
			return ResultInfo.setFalse(getRequestId(request));
		}
	}

}
