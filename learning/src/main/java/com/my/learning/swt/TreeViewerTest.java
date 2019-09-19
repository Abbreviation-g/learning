package com.my.learning.swt;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

public class TreeViewerTest {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(500, 800);
		shell.setLayout(new GridLayout(2, false));

		Text text = new Text(shell, SWT.BORDER | SWT.SINGLE);
		text.setToolTipText("输入路径，扫描文件夹");
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Button button = new Button(shell, SWT.PUSH);
		button.setText("扫描");
		GridData buttonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonGridData.widthHint = 100;
		button.setLayoutData(buttonGridData);

		TreeViewer viewer = new TreeViewer(shell);
		Tree tree = viewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));

		viewer.setContentProvider(new MyContentProvider());
		viewer.setLabelProvider(new MyLabelProvider());

		button.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			viewer.setInput(new File(text.getText()));
		}));
		
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			File file = (File) inputElement;
			if (file.isFile()) {
				return null;
			} else if (file.isDirectory()) {
				return file.listFiles();
			}
			return null;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			File file = (File) parentElement;
			if (file.isFile()) {
				return null;
			} else if (file.isDirectory()) {
				return file.listFiles();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			File file = (File) element;
			return file.getParentFile();
		}

		@Override
		public boolean hasChildren(Object element) {
			File file = (File) element;
			if (file.isFile()) {
				return false;
			} else if (file.isDirectory()) {
				return true;
			}
			return false;
		}
	}

	private static class MyLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof File) {
				return ((File) element).getName();
			}
			return null;
		}
	}
}
