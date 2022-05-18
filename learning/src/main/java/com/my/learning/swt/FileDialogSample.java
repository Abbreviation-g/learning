package com.my.learning.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.my.learning.swt.ListExample.DeselectAdapter;

public class FileDialogSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		FileDialog dialog = new FileDialog(shell,SWT.OPEN);
		dialog.setFilterExtensions(new String[] {"*.txt;*.text","*.xls;xlsx","*.csv"});
		dialog.setFilterNames(new String[] {"纯文本文件","excel文件"});
		dialog.open();

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
