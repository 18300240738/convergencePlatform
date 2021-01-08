package com.qr.interceptor;

import com.qr.domain.ResultCode;
import com.qr.encryption.SM4Utils;
import com.qr.log.LogHandler;
import com.qr.result.ResultInfo;
import com.qr.service.ISysTokenVerifyService;
import com.qr.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

/**
 *  生成请求id拦截器
 * @Author wd
 * @Date 13:16 2020/8/31
 **/

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

	@Autowired
	private ISysTokenVerifyService sysTokenVerifyService;
	@Value("${time.config.expireTime}")
	private Long expireTime;

	/**
	 *  方法执行前
	 * @Author wd
	 * @Date 13:17 2020/8/31
	 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, LogHandler> logHandlerMap = SpringContextUtil.getApplicationContext().getBeansOfType(LogHandler.class);
		boolean aBoolean = true;

		//处理调用成功数据
		for (Map.Entry<String, LogHandler> e : logHandlerMap.entrySet()) {
			LogHandler value = e.getValue();
			value.interfaceCount(null);
		}


		String requestId = UUID.randomUUID().toString().replace("-", "");
		MDC.put("requestId", requestId);
		//*********** 设置requestId ***********
		request.setAttribute("requestId",requestId);

		//*********** 设置requestId ***********
		String timestamp = request.getHeader("timestamp");
		String token = request.getHeader("token");
		Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp), "参数错误");

		//*********** 请求时间间隔 ***********
		long reqeustInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
		Assert.isTrue(reqeustInterval < expireTime, "请求超时，请重新请求");

		//*********** token校验 ***********
		aBoolean = sysTokenVerifyService.tokenVerify(token);
		if (Boolean.FALSE.equals(aBoolean)) {
			response.sendRedirect("/converge/401");
		}

		//*********** 设置数据解密key ***********
		String sm4Key = sysTokenVerifyService.getSm4Key(token);

		//*********** 数据加密验证 ***********
		@NotNull String data = request.getParameter("data");

		SM4Utils sm4 = new SM4Utils();
		sm4.setSecretKey(sm4Key);
		sm4.setHexString (false);
		data = sm4.decryptDataECB(data);
		if (StringUtils.isBlank(data)) {
			response.sendRedirect("/converge/501");
			aBoolean = false;
		}

		request.setAttribute("data",data);

		//处理调用成功数据
		for (Map.Entry<String, LogHandler> entry : logHandlerMap.entrySet()) {
			LogHandler v = entry.getValue();
			v.interfaceCount(aBoolean);
		}
		return aBoolean;
	}

	/**
	 *  方法执行后
	 * @Author wd
	 * @Date 13:17 2020/8/31
	 **/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// Do nothing because of X and Y.
	}

	/**
	 *  页面渲染前
	 * @Author wd
	 * @Date 13:17 2020/8/31
	 **/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
			// Do nothing because of X and Y.
	}
}
