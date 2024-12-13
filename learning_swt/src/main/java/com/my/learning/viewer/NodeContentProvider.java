package com.my.learning.viewer;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class NodeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof INode) {
			INode node = (INode) inputElement;
			List<INode> children = node.getChildren();
			if (children == null || children.isEmpty())
				return new Object[] {  };
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
		if (element instanceof INode) {
			INode node = (INode) element;
			return node.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof INode) {
			INode node = (INode) element;
			List<INode> children = node.getChildren();
			if (children == null || children.isEmpty())
				return false;
			return true;
		}
		return false;
	}
}
