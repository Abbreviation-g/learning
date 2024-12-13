package com.my.learning.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class ShellOnTopTest {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2,false));
		shell.setMinimumSize(400, 200);
		
		Group group = new Group(shell, SWT.SHADOW_IN);
		group.setText(" self-adaption ");
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 400;
		gd.heightHint = 200;
		group.setLayoutData(gd);
		group.setLayout(new FillLayout());
		Button onTopButton = new Button(group, SWT.CHECK);
		onTopButton.setText("Always On Top");
		onTopButton.addSelectionListener(SelectionListener.widgetSelectedAdapter((e)->{
			if(onTopButton.getSelection()) {
				OS.SetWindowPos(shell.handle, OS.HWND_TOPMOST, 0, 0, 0, 0, OS.SWP_NOMOVE|OS.SWP_NOSIZE);
			} else {
				OS.SetWindowPos(shell.handle, OS.HWND_NOTOPMOST, 0, 0, 0, 0, OS.SWP_NOMOVE|OS.SWP_NOSIZE);
			}
		}));       
		
		Group group2 = new Group(shell, SWT.SHADOW_IN);
		group2.setText("group2");
		GridData gd2 = new GridData(SWT.FILL, SWT.FILL, false, true);
		gd2.widthHint = 200;
		group2.setLayoutData(gd2);
		group2.setLayout(new GridLayout());
		Button button2 = new Button(group2, SWT.PUSH);
		button2.setText("Button");
		button2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		
		shell.pack();
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
