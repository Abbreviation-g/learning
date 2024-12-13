package com.my.learning.leetcode;

import java.util.Scanner;

public class Int数据存储1的个数 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int nextInt = scanner.nextInt();
			scanner.nextLine();
			
			String binaryString = Integer.toBinaryString(nextInt);
			int count1 = 0;
			for(char ch: binaryString.toCharArray()) {
				if(ch == '1') {
					count1 ++;
				}
			}
			System.out.println(count1);
		}
		
		scanner.close();
	}
}
