package com.my.learning.leetcode;

import java.util.Scanner;

/**
 * 蛇形矩阵是由1开始的自然数依次排列成的一个矩阵上三角形。
 * 
 * 例如，当输入5时，应该输出的三角形为：
 * 
 * 1 3 6 10 15
 * 
 * 2 5 9 14
 * 
 * 4 8 13
 * 
 * 7 12
 * 
 * 11
 * 
 * 请注意本题含有多组样例输入。
 * 
 * @author guoenjing
 *
 */
public class 蛇形矩阵 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			int nextInt = scanner.nextInt();
			scanner.nextLine();

			print(nextInt);
		}

		scanner.close();
	}

	private static void print(int nextInt) {

		int first_col = 1;
		for (int i = 1; i <= nextInt; i++) {
			System.out.print(first_col);
			int tmp = first_col;
			for (int j = i + 1; j <= nextInt; j++) {
				tmp += j;
				System.out.print(" " + tmp);
			}
			System.out.println();
			first_col += i;
		}
	}
}
