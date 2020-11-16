package com.my.learning.leetcode;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class 遍历文件夹 {
	public static void main(String[] args) {
		File folder = new File("C:\\work\\sorce_code\\learning\\learning\\src\\main\\java\\com\\my\\learning");
		List<File> scaned = scanFolder(folder);
		System.out.println(scaned.size());
	}
	
	public static List<File> scanFolder(File folder) {
		List<File> scanedFile = new ArrayList<File>();
		if(!folder.isDirectory()) {
			return scanedFile;
		}
		Queue<File> folderNeedToScan = new LinkedList<>();
		File[] files = folder.listFiles();
		for(File subFile : files) {
			if(subFile.isFile()) {
				scanedFile.add(subFile);
			} else if(subFile.isDirectory()) {
				folderNeedToScan.add(subFile);
			}
		}
		while(!folderNeedToScan.isEmpty()) {
			File scanedFolder = folderNeedToScan.poll();
			for(File subFile: scanedFolder.listFiles()) {
				if(subFile.isFile()) {
					scanedFile.add(subFile);
				} else if(subFile.isDirectory()) {
					folderNeedToScan.add(subFile);
				}
			}
		}
		return scanedFile;
	}
}
