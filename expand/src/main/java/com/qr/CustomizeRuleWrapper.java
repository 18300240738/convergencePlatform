package com.qr;

import com.qr.customize.CustomizeRule;
import com.qr.customize.Wrapper;

@Wrapper
public class CustomizeRuleWrapper implements CustomizeRule {
	@Override
	public boolean verify() {
		System.err.println("执行扩展校验1");
		return true;
	}
}
