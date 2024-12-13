package com.my.learning.swt;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ComboTest {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(3, false));

		Combo combo = new Combo(shell, SWT.SIMPLE);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.add("aaa");
		combo.add("aba");
		combo.add("abb");
		combo.add("abc");
		combo.add("aca");
		combo.add("acb");
		combo.add("acc");
		combo.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					System.out.println("keyReleased: " + "delete");
					return;
				} else if (e.keyCode == SWT.BS) {
					System.out.println("keyReleased: " + "backspace");
					return;
				} else /* if(e.keyCode == SWT.CR) */ {
					System.out.println("keyReleased: " + "return");
					String[] items = combo.getItems();
					String enterText = combo.getText();
					for (String item : items) {
						if (item.startsWith(enterText)) {
							int matchingPosition = enterText.length();
							combo.setText(item);
							combo.setSelection(new Point(matchingPosition, item.length()));
							break;
						}
					}
				}
			}
		});
		printStackTrack0();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	static void printStackTrack0() {
		printStackTrack1();
	}

	static void printStackTrack1() {
		printStackTrack2();
	}

	static void printStackTrack2() {
		printStackTrack();
	}

	static void printStackTrack() {
		try {
			new Throwable().printStackTrace();
		} catch (Exception e) {
		}
	}
}
