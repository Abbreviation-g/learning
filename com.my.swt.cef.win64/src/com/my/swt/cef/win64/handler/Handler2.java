package com.my.swt.cef.win64.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class Handler2 extends AbstractHandler {

	static boolean isEnable = true;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Handler2.execute()");
		return null;
	}

	@Override
	public boolean isEnabled() {
		return isEnable;
	}
}
