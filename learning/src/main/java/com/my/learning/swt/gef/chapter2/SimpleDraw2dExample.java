package com.my.learning.swt.gef.chapter2;

import java.awt.peer.LightweightPeer;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SimpleDraw2dExample {
	private Composite parent;
	private SimpleDraw2dExample(Composite parent) {
		this.parent = parent;
	}

	private void createContent() {
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		LightweightSystem lightweightSystem = new LightweightSystem(canvas);
		
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Chapter 2 - A simple Draw2D example");
		shell.setLayout(new GridLayout());
		
		new SimpleDraw2dExample(shell);
		shell.open();
		shell.pack();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
