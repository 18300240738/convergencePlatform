package com.qr.signature.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qr.base.BaseController;
import com.qr.result.ResultInfo;
import com.qr.signature.entity.DeveloperInfo;
import com.qr.signature.service.IDeveloperInfoService;
import com.qr.signature.service.ITokenInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import static com.qr.domain.ResultCode.INVALID_PARAM;
import static com.qr.domain.ResultCode.VALIDATE_FAILED;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wd
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/convergence/signature")
@Api(value = "token信息发放",tags = "token")
public class TokenInfoController extends BaseController {


	@Autowired
	private IDeveloperInfoService developerInfoService;
	@Autowired
	private ITokenInfoService tokenInfoService;

	@RequestMapping("/getToken")
	@ApiOperation(value="获取token信息", notes="token")
	public ResultInfo<String> getToken(String appId,String appSecret,HttpServletRequest request){

		if(StringUtils.isBlank(appId)) {
			return customizeResult(VALIDATE_FAILED.getCode(), VALIDATE_FAILED.getMessage(), getRequestId(request), null);
		}
		if(StringUtils.isBlank(appSecret)) {
			return customizeResult(VALIDATE_FAILED.getCode(), VALIDATE_FAILED.getMessage(), getRequestId(request), null);
		}

		//*************************** 验证开发者信息 ***************************
		DeveloperInfo developerInfo = developerInfoService.getOne(new LambdaQueryWrapper<DeveloperInfo>().eq(DeveloperInfo::getAppid, appId)
				.eq(DeveloperInfo::getAppSecret, appSecret));
		if (developerInfo == null) {
			return customizeResult(INVALID_PARAM.getCode(), INVALID_PARAM.getMessage(), getRequestId(request), null);
		}
		//*************************** 生成token ***************************
		String token = tokenInfoService.getToken(developerInfo,appId,appSecret);
		return trueResult(token,request);

	}

}
