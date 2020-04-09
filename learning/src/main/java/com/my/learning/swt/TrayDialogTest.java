package com.my.learning.swt;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TrayDialogTest {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Shell");
		TrayDialog trayDialog = new TrayDialog(shell) {
		};
		
		trayDialog.open();
//		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
