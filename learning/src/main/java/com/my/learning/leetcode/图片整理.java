package com.my.learning.leetcode;

import java.util.Arrays;
import java.util.Scanner;

public class 图片整理 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			char[] charArray = line.toCharArray();
			Arrays.sort(charArray);
			System.out.println(new String (charArray));
		}
		
		scanner.close();
	}
}
