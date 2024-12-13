package com.my.learning.viewer;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TreeViewerColumnEditTest {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell();
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(800, 600);

		new TreeViewerColumnEditTest(shell).createControl();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Shell shell;

	public TreeViewerColumnEditTest(Shell shell) {
		this.shell = shell;
	}

	public Composite createControl() {

		createDirectoryGroup();
		createTreeViewer();
		createButtons();
		return null;
	}

	private void createDirectoryGroup() {
		Group textGroup = new Group(shell, SWT.SHADOW_IN);
		textGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		GridLayout groupLayout = new GridLayout(3, false);
		textGroup.setLayout(groupLayout);
		textGroup.setText("选择路径");

		new Label(textGroup, SWT.NONE).setText("Directory: ");
		Text directoryText = new Text(textGroup, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		directoryText.setToolTipText("Directory");
		directoryText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Button directoryButton = new Button(textGroup, SWT.PUSH);
		directoryButton.setText("Browser");
		GridData buttonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonGridData.widthHint = 80;
		directoryButton.setLayoutData(buttonGridData);
	}

	private void createTreeViewer() {
		TreeViewer viewer = new TreeViewer(shell,SWT.FULL_SELECTION|SWT.BORDER);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		INode root = new Node();
		root.addChild(Node.scanFolder("C:\\work\\sources\\hello-rcp\\hello-rcp\\com.my.hello.editor.filetree"));
		
		TreeViewerColumn nameColumn = new TreeViewerColumn(viewer, SWT.NONE);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setWidth(200);
		TreeViewerColumn dateColumn = new TreeViewerColumn(viewer, SWT.NONE);
		dateColumn.getColumn().setText("Date");
		dateColumn.getColumn().setWidth(200);
		
		viewer.setContentProvider(new NodeContentProvider());
		viewer.setLabelProvider(new NodeLabelProvider());
		viewer.setInput(root);
	}

	private void createButtons() {
		Composite buttonComposite = new Composite(shell, SWT.NONE);
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.marginWidth = 2;
		buttonLayout.marginRight = 5;
		GridData compGridData = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);

		buttonComposite.setLayoutData(compGridData);
		buttonComposite.setLayout(buttonLayout);
		Button editButton = buttonFactory(buttonComposite, "Edit", null);
		Button addButton = buttonFactory(buttonComposite, "Add", null);
		Button removeButton = buttonFactory(buttonComposite, "Remove", null);
	}

	private Button buttonFactory(Composite parent, String text, SelectionListener selectionListener) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		
		GridData buttonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonGridData.widthHint = 80;
		button.setLayoutData(buttonGridData);
		if (selectionListener != null) {
			button.addSelectionListener(selectionListener);
		}
		return button;
	}
}
