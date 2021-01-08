package com.qr.exception;

import com.qr.result.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 *
 * @author wd
 */
@RestControllerAdvice
@Slf4j
public class RRExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public ResultInfo handleRRException(RRException e,HttpServletRequest request){
		log.error("自定义异常: {}",e.getMsg());
		ResultInfo r = ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")), e.getMsg());
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return r;
	}

	/**
	 *  数据校验异常
	 * @Author wd
	 * @Description //TODO
	 * @Date 11:42 2019/10/11
	 **/
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public ResultInfo handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
		log.error("数据校验异常: {}",e.getMessage());
		BindingResult bindingResult = e.getBindingResult();
		String errorMesssage = "";

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage = errorMesssage+fieldError.getField()+fieldError.getDefaultMessage();
		}

		ResultInfo r = ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")), errorMesssage);
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return r;
	}

//	@ExceptionHandler(value = ConstraintViolationException.class)
//	@ResponseBody
//	public String ConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
//		log.error("数据校验异常: {}",e.getMessage());
//
//		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//		final String[] message = {""};
//		constraintViolations.forEach(constraintViolation->{
//			message[0] += constraintViolation.getMessage();
//		});
//
//		R r = R.setFalse(String.valueOf(request.getAttribute("requestId")), message[0]);
//		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
//		return result;
//	}

	/**
	 *  数据验证异常
	 * @Author wd
	 * @Description //TODO
	 * @Date 17:05 2020/7/6
	 **/
	@ExceptionHandler(IllegalArgumentException.class)
	public ResultInfo IllegalArgumentException(IllegalArgumentException e,HttpServletRequest request){
		log.error("数据接口超时异常: {}",e.getMessage());

		ResultInfo r = ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")), e.getMessage());
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return r;
	}

	/**
	 *  权限验证异常
	 * @Author wd
	 * @Description //TODO
	 * @Date 10:40 2019/10/11
	 **/
//	@ExceptionHandler(AuthorizationException.class)
//	public String authorizationException (AuthorizationException authorizationException,HttpServletRequest request){
//		log.error("权限验证异常: {}",authorizationException.getMessage());
//
//		R r = R.setR(401,"登录失效,请重新登录!",String.valueOf(request.getAttribute("requestId")),"登录失效,请重新登录!");
//		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
//		return result;
//	}

	@ExceptionHandler(AuthenticationException.class)
	public ResultInfo authorizationException (AuthenticationException authorizationException,HttpServletRequest request){
		log.error("token信息失效 : {}",authorizationException.getMessage());

		ResultInfo resultInfo = ResultInfo.setR(401L,"登录失效,请重新登录!",String.valueOf(request.getAttribute("requestId")),"登录失效,请重新登录!");
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return resultInfo;
	}

	/**
	 *  系统运行时异常
	 * @Author mz
	 * @Description //TODO
	 * @Date 10:40 2019/10/11
	 **/
	@ExceptionHandler(RuntimeException.class)
	public ResultInfo runtimeException (RuntimeException e,HttpServletRequest request){
		log.error("系统运行时异常: {}",e);

		ResultInfo r = ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")),"系统异常,请联系管理员!");
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return r;
	}

	/**
	 *  系统异常
	 * @Author wd
	 * @Description //TODO
	 * @Date 10:40 2019/10/11
	 **/
	@ExceptionHandler(Exception.class)
	public ResultInfo handleException(Exception e,HttpServletRequest request){
		log.error("系统异常: {}",e);

		ResultInfo r = ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")), "系统异常,请联系管理员!");
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return r;
	}

	/**
	 *  捕获自定义sql异常
	 * @Author wd
	 * @Description //TODO
	 * @Date 9:24 2020/7/9
	 **/
	@ExceptionHandler(UncategorizedSQLException.class)
	public ResultInfo UncategorizedSQLException(UncategorizedSQLException e, HttpServletRequest request){
		log.error("数据异常: {}",e);

		ResultInfo r =  ResultInfo.setFalse(String.valueOf(request.getAttribute("requestId")),e.getSQLException().getLocalizedMessage());
		//生成key
//		String result = AesUtils.encryptAES(JSONObject.toJSONString(r, SerializerFeature.DisableCircularReferenceDetect), QrGatewayContext.key);
		return r;
	}

//	private WxSysUser getUserInfo(HttpServletRequest request){
//		WxSysUser wxSysUser = null;
//		String token = request.getHeader("TOKEN");
//		if (StringUtils.isNotBlank(token)) {
//			String userInfoStr = RedisUtils.get(stringRedisTemplate, token);
//			if (StringUtils.isBlank(userInfoStr))
//				return null;
//			JSONObject jsonObject = JSONObject.parseObject(userInfoStr);
//			String user = jsonObject.getString("user");
//			wxSysUser = JSONObject.parseObject(user, WxSysUser.class);
//		}else {
//			wxSysUser = new WxSysUser();
//		}
//		return wxSysUser;
//	}
}
