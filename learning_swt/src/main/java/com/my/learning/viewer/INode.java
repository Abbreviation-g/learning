package com.my.learning.viewer;

import java.io.File;
import java.util.List;

public interface INode {
	public void setFile(File file);

	public INode getParent();

	public void setParent(INode parent);

	public List<INode> getChildren();

	public File getFile();

	public boolean addChild(INode child);

	public boolean remove(INode child);
	
}
