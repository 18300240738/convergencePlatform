package com.qr;

import com.qr.customize.CustomizeRule;
import com.qr.customize.Wrapper;

@Wrapper
public class CustomizeRuleWrapper1 implements CustomizeRule {
	@Override
	public boolean verify() {
		System.err.println("执行扩展校验2");
		return false;
	}
}
