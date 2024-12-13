package com.my.learning.viewer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Node implements INode {
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_INIT = "PROPERTY_INIT";
	public static final String PROPERTY_ADD = "PROPERTY_ADD";
	public static final String PROPERTY_REMOVE = "PROPERTY_REMOVE";

	private INode parent;
	private File file;
	private List<INode> children;

	public Node() {
		init();
		firePropertyChange(PROPERTY_INIT, null, this);
	}

	public void init() {
		this.parent = null;
		this.file = null;
		this.children = new ArrayList<INode>();
		this.listeners = new PropertyChangeSupport(this);
	}

	public void setFile(File file) {
		this.file = file;
	}

	public INode getParent() {
		return parent;
	}

	public void setParent(INode parent) {
		this.parent = parent;
	}

	public List<INode> getChildren() {
		return children;
	}

	public File getFile() {
		return file;
	}

	public boolean addChild(INode child) {
		boolean result = children.add(child);
		if (result) {
			firePropertyChange(PROPERTY_ADD, null, child);
		}
		return result;
	}

	public boolean remove(INode child) {
		boolean result = children.remove(child);
		if (result) {
			firePropertyChange(PROPERTY_REMOVE, child, null);
		}
		return result;
	}

	@Override
	public String toString() {
		return file.getName();
	}

	public static INode scanFolder(String folderPath) {
		return scanFolder(folderPath, null);
	}

	public static INode scanFolder(String folderPath, PropertyChangeListener propertyChangeListener) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			return null;
		}
		Node node = new Node();
		node.setFile(folder);
		if (propertyChangeListener != null) {
			node.addPropertyChangeListener(propertyChangeListener);
		}
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				INode child = scanFolder(file.getAbsolutePath());
				child.setParent(node);
				node.addChild(child);
			}
		}

		return node;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.listeners.removePropertyChangeListener(listener);
	}

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this.listeners.firePropertyChange(propertyName, oldValue, newValue);
	}
}
