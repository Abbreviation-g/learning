package com.my.swt.cef.win64.handler;

import java.util.Collection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;

public class Handler1 extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Handler1.execute()");
		HandlerPropertyTester.Handler2_Enable = false;
		
//		Collection activeContexts = HandlerUtil.getActiveContexts(event);
//		System.out.println("----------------");
//		System.out.println(activeContexts);
//		
//		IHandlerService handlerService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(IHandlerService.class);
//		handlerService.activateHandler(activation)
		return null;
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled();
	}
}
