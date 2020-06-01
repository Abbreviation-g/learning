package com.my.learning.leetcode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class 求多个文件路径的最大共同父路径 {
	public static void main(String[] args) {
		List<String > filePaths = new ArrayList<String>();
		filePaths.add("C:/f1/f11/f111.c");
		filePaths.add("C:/f1/f11/f1122.c");
//		filePaths.add("C:/f1/f12/f121.c");
//		filePaths.add("C:/f1/f12/f122.c");
		List<String[]> allSegments = pathsToSegments(filePaths);
		
		System.out.println(getCommonSegments(allSegments));
	}
	
	public static List<String> getCommonSegments(List<String[]> allSegments){
		List<String> commonSegments = new ArrayList<String>(); 
		
		String currentSegment = null;
		int index = 0;
		while((currentSegment = hasCommonSegment(allSegments, index)) != null) {
			index ++;
			commonSegments.add(currentSegment);
		}
		return commonSegments;
	}
	
	public static String hasCommonSegment(List<String[]> allSegments,int index) {
		String currentSegment = null;
		for (String[] segment : allSegments) {
			if(index <0 || index > segment.length - 1) {
				return null;
			}
			if(currentSegment == null) {
				currentSegment = segment[index] ;
			} else if(!segment[index].equals(currentSegment)){
				return null;
			}
		}
		return currentSegment;
	}
	
	public static List<String[]> pathsToSegments(List<String> filePaths){
		List<String[]> allSegments = filePaths.stream().map(求多个文件路径的最大共同父路径::pathToSegments).collect(Collectors.toList());
		return allSegments;
	}
	
	public static String[] pathToSegments(String filePath) {
		Path path = Paths.get(filePath);
		int nameCount = path.getNameCount();
		String[] segments = new String[nameCount+1];
		segments[0] = path.getRoot().toString();
		for (int i = 0; i < nameCount; i++) {
			segments[i+1] = path.getName(i).toString();
		}
		return segments;
	}
	 
}
