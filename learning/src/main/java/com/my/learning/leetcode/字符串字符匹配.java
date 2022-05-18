package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 字符串字符匹配 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String shortWord = scanner.nextLine();
			String longWord = scanner.nextLine();
			System.out.println(checkLetter(shortWord, longWord));
			
		}
		scanner.close();
	}
	
	private static boolean checkLetter(String shortWord, String longWord) {
		List<Character> longChars = new ArrayList<Character>();
		for (char longChar : longWord.toCharArray()) {
			longChars.add(longChar);
		}
		for(char shortChar: shortWord.toCharArray()) {
			if(!longChars.contains(shortChar)) {
				return false;
			}
		}
		return true;
	}
}
