package com.my.learning.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CComboText {
	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(3, false));
		
		CCombo combo = new CCombo(shell, SWT.BORDER);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.add("aaa");
		combo.add("abb");
		combo.add("acc");
		combo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode == SWT.DEL) {
					System.out.println("keyReleased: "+"delete");
				} else if(e.keyCode == SWT.BS) {
					System.out.println("keyReleased: "+"backspace");
				} else if(e.keyCode == SWT.CR) {
					System.out.println("keyReleased: "+"return");
//					String[] item = combo.getItems();
//					String enterText = combo.getText();
//					for (int i = 0; i < item.length; i++) {
//						if(item[i].startsWith(enterText)) {
//							combo.select(i);
//						}
//					}
					String[] items = combo.getItems();
					String enterText = combo.getText();
					int index = -1;
					for (int i = 0; i < items.length; i++) {
						if (items[i].toLowerCase().startsWith(enterText.toLowerCase())) {
							index = i;
							break;
						}
					}

					if (index != -1) {
						Point pt = combo.getSelection();
						combo.select(index);
						combo.setText(items[index]);
						combo.setSelection(new Point(pt.x, items[index].length()));
					}
				}
			}
		});
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	
	}
}
