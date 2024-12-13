package com.my.learning.swt;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class ListExample {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));

        List list = new List(shell, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        list.setLayoutData(new GridData(GridData.FILL_BOTH));
        list.setItems(new String[] { "0", "1", "2", "3", "4", "5" });
        list.addSelectionListener(new DeselectAdapter());

        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    public static class DeselectAdapter extends SelectionAdapter {
        private int singleSelected = -1;

        @Override
        public void widgetSelected(SelectionEvent e) {
            List list = (List) e.widget;
            if(singleSelected == list.getSelectionIndex()) {
                list.deselect(singleSelected);
            }
            singleSelected = list.getSelectionIndex();
        }
    }
}
