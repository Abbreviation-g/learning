package com.my.learning.viewer;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class NodeLabelProvider extends LabelProvider implements ITableLabelProvider {

//	@Override
//	public String getText(Object element) {
//		if (element instanceof INode) {
//			INode entry = (INode) element;
//			if (entry.getFile() == null) {
//				return null;
//			}
//			return entry.getFile().getName();
//		}
//		return null;
//	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
			INode entry = (INode) element;
			if (entry.getFile() == null) {
				return null;
			}
			if(columnIndex == 0) {
				return entry.getFile().getName();
			} else {
				return Long.valueOf(entry.getFile().lastModified()).toString();
			}
	}
}
