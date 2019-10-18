package com.my.learning.swt.nebula;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.nebula.widgets.opal.commons.SWTGraphicUtil;
import org.eclipse.nebula.widgets.opal.propertytable.PTProperty;
import org.eclipse.nebula.widgets.opal.propertytable.PropertyTable;
import org.eclipse.nebula.widgets.opal.propertytable.editor.PTDirectoryEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NebulaTree {
	public static void main(String[] args) {
		Locale.setDefault(Locale.CHINESE);
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Eclipse Nebula Tree");
		shell.setLayout(new GridLayout());

		PropertyTable searchTable = new PropertyTable(shell, SWT.NONE);
		searchTable.hideDescription();
		searchTable.hideButtons();
		GridData searchGD = new GridData(SWT.FILL, SWT.FILL, true, false);
		searchGD.heightHint = 70;
		searchTable.addProperty(new PTProperty("searchHome", "Directory", "").setEditor(new PTDirectoryEditor()));
		searchTable.viewAsFlatList();
		searchTable.setLayoutData(searchGD);

		PropertyTable table = new PropertyTable(shell, SWT.NONE);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.hideDescription();
		table.viewAsCategories();
		table.hideButtons();

		searchTable.addChangeListener((property) -> {
			String folder = (String) property.getValue();
			if (folder != null) {
				Node root = new Node();
				root.addChild(scanFolder(folder));
				System.out.println(root);
				initPropertyTable(table, root, root.toString());
				table.refreshValues();
			}
		});

		SWTGraphicUtil.centerShell(shell);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void initPropertyTable(PropertyTable table, Node root, String category) {
		List<Node> children = root.getChildren();
		if (children == null || children.isEmpty()) {
			return;
		}
		for (Node node : children) {
			String subCategory = category + "/" + node.toString();
			table.addProperty(new PTProperty(subCategory, node.getFile().getName(), node.getFile().getPath(),
					node.getFile().getPath()).setCategory(category));
			initPropertyTable(table, node, subCategory);
		}

	}

	static Node scanFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			return null;
		}
		Node node = new Node();
		node.setFile(folder);
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (files != null && files.length >= 1) {
				for (File file : folder.listFiles()) {
					Node child = scanFolder(file.getAbsolutePath());
					child.setParent(node);
					node.addChild(child);
				}
			}
		}

		return node;
	}

	private static class Node {
		private Node parent;
		private File file;
		private List<Node> children = new ArrayList<Node>();

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
			return (file == null) ? "root" : file.getName();
		}
	}
}
