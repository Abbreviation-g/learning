package com.my.swt.cef.win64.handler;

import org.eclipse.core.expressions.PropertyTester;

public class HandlerPropertyTester extends PropertyTester {

	public static boolean Handler2_Enable = true;
	
	public HandlerPropertyTester() {
		System.out.println("HandlerPropertyTester.HandlerPropertyTester()");
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		return Handler2_Enable;
	}

}
