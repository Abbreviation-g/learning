package com.my.learning.swt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
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

public class TreeViewerTest2 {
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

		TreeViewer viewer = new TreeViewer(shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Tree tree = viewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
		viewer.setContentProvider(new MyContentProvider());
		viewer.setLabelProvider(new MyLabelProvider());

		Button removeButton = new Button(shell, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		removeButton.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			Node node = (Node) selection.getFirstElement();
			if (node == null)
				return;

			Node parentNode = node.getParent();
			if (parentNode == null) {
				viewer.setInput(null);
				return;
			}
			parentNode.remove(node);

			viewer.refresh(parentNode);
		}));
		Button addButton = new Button(shell, SWT.PUSH);
		addButton.setText("Add");
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addButton.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			Node parentNode = (Node) selection.getFirstElement();
			if (parentNode == null) {
				Node child = new Node();
				child.setFile(new File("Child"));

				Node root = new Node();
				root.addChild(child);

				viewer.setInput(root);
				return;
			}
			Node child = new Node();
			child.setFile(new File(parentNode.getFile(), "Child"));
			child.setParent(parentNode);

			parentNode.addChild(child);
			viewer.refresh(parentNode);
		}));

		button.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			Node list = scanFolder(text.getText());
			if (list == null)
				return;
			System.out.println(list);

			Node root = new Node();
			root.addChild(list);

			viewer.setInput(root);
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

	static Node scanFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			return null;
		}
		Node node = new Node();
		node.setFile(folder);
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				Node child = scanFolder(file.getAbsolutePath());
				child.setParent(node);
				node.addChild(child);
			}
		}

		return node;
	}

	private static class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Node) {
				Node node = (Node) inputElement;
				List<Node> children = node.getChildren();
				if (children == null || children.isEmpty())
					return new Object[] { node };
				return children.toArray();
			}
			return null;
		}

		@Override
		public Object[] getChildren(Object inputElement) {
			return getElements(inputElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof Node) {
				Node node = (Node) element;
				return node.getParent();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Node) {
				Node node = (Node) element;
				List<Node> children = node.getChildren();
				if (children == null || children.isEmpty())
					return false;
				return true;
			}
			return false;
		}
	}

	private static class MyLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof Node) {
				Node entry = (Node) element;
				return entry.getFile().getName();
			}
			return null;
		}
	}

	private static class Node {
		private Node parent;
		private File file;
		private List<Node> children = new ArrayList<TreeViewerTest2.Node>();

		public void setFile(File file) {
			this.file = file;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public List<Node> getChildren() {
			return children;
		}

		public File getFile() {
			return file;
		}

		public boolean addChild(Node child) {
			return children.add(child);
		}

		public boolean remove(Node child) {
			return children.remove(child);
		}

		@Override
		public String toString() {
			return file.getName();
		}
	}
}
