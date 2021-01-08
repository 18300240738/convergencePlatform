package com.qr.interceptor;

import com.qr.wrapper.XssHttpServletRequestWrapper;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *  防止XSS攻击过滤器
 * @Author wd
 * @since 11:41 2020/9/23
 **/
@Configuration
public class XssFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing because of X and Y.
	}

	@Override
	public void destroy() {
		// Do nothing because of X and Y.

	}
}
