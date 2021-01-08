package com.qr.dataobject.controller;

import com.google.common.collect.Maps;
import com.qr.base.BaseController;
import com.qr.dataobject.entity.*;
import com.qr.dataobject.service.*;
import com.qr.result.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 与核心层交互接口
 * @Author wd
 * @since 10:52 2020/9/25
 **/
@RestController
@RequestMapping("/sys/basicData")
@Api(value = "系统交互层",tags = "核心层交互")
public class CoreInfoController extends BaseController {

	@Autowired
	private IDataFlowInfoService dataFlowInfoService;
	@Autowired
	private IDataNamesService dataNamesService;
	@Autowired
	private IDataFieldsService dataFieldsService;
	@Autowired
	private IFieldRuleService fieldRuleService;
	@Autowired
	private IRuleInfosService ruleInfosService;
	@Autowired
	private IDataNamesFlowService dataNamesFlowService;

	@PostMapping("/info")
	@ApiOperation(value="数据对象流向 名称 属性信息", notes="核心层交互")
	public ResultInfo info(HttpServletRequest request){
		Map result = Maps.newHashMap();
		List<DataFlowInfo> dataFlowInfos = dataFlowInfoService.list();
		result.put("flowInfos",dataFlowInfos);
		List<DataNamesFlow> dataNamesFlows = dataNamesFlowService.list();
		result.put("dataNamesFlows",dataNamesFlows);

		return ResultInfo.setTrueObject(getRequestId(request),result);
	}

	@PostMapping("/fieldRuleInfo")
	@ApiOperation(value="数据对象规则 属性对应规则信息", notes="核心层交互")
	public ResultInfo fieldRuleInfo(HttpServletRequest request){
		Map result = Maps.newHashMap();
		List<FieldRule> fieldRuleList = fieldRuleService.list();
		result.put("fieldRules",fieldRuleList);
		List<RuleInfos> ruleInfos = ruleInfosService.list();
		result.put("ruleInfos",ruleInfos);
		List<DataNames> dataNames = dataNamesService.list();
		result.put("dataNames",dataNames);
		List<DataFields> dataFields = dataFieldsService.list();
		result.put("dataFields",dataFields);
		return ResultInfo.setTrueObject(getRequestId(request),result);
	}
}

