package com.my.learning.leetcode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 将文件列表转换为文件树 {
	public static class Node extends File {
		private static final long serialVersionUID = 1L;
		private List<Node> children;
		private String[] segments;

		public Node(String[] segments) {
			super(segmentsToPath(segments));
			this.segments = segments;
			this.children = new ArrayList<Node>();
		}

		public void addChild(Node child) {
			if (child == null) {
				return;
			}
			int index = children.indexOf(child);
			if (index == -1) {
				children.add(child);
			}
		}

		public String[] getSegments() {
			return segments;
		}

		@Override
		public File[] listFiles() {
			return children.toArray(new Node[children.size()]);
		}

		public static String segmentsToPath(String[] segments) {
			if (segments == null || segments.length == 0) {
				return null;
			}
			String rootSegment = segments[0];
			String[] restSegments = Arrays.copyOfRange(segments, 1, segments.length);
			Path path = Paths.get(rootSegment, restSegments);
			return path.toString();
		}

		public static Node createAllNode(Node rootNode, List<Node> allNodes, String[] segments) {
			if (Arrays.equals(rootNode.getSegments(), segments)) {
				return rootNode;
			}
			for (Node currentNode : allNodes) {
				if (Arrays.equals(currentNode.getSegments(), segments)) {
					return currentNode;
				}
			}
			Node currentNode = new Node(segments);
			allNodes.add(currentNode);
			Node parentNode = createAllNode(rootNode, allNodes, Arrays.copyOf(segments, segments.length - 1));
			parentNode.addChild(currentNode);
			return currentNode;
		}
		
		public static Node filePathToTreeNode(List<String> filePaths) {
			List<String[]> allSegments = 求多个文件路径的最大共同父路径.pathsToSegments(filePaths);
			List<String> commonSegment = 求多个文件路径的最大共同父路径.getCommonSegments(allSegments);
			Node rootNode = new Node(commonSegment.toArray(new String[commonSegment.size()]));
			List<Node> tempAllNode = new ArrayList<Node>();
			for(String[] segments : allSegments) {
				Node.createAllNode(rootNode, tempAllNode, segments);
			}
			return rootNode;
		}
	}

	public static void main(String[] args) {
		List<String > filePaths = new ArrayList<String>();
		filePaths.add("C:/f1/f11/f111.c");
		filePaths.add("C:/f1/f11/f1122.c");
		filePaths.add("C:/f1/f12/f121.c");
		filePaths.add("C:/f1/f12/f122.c");
		
		Node rootNode = Node.filePathToTreeNode(filePaths);
		System.out.println(rootNode);
	}
}
