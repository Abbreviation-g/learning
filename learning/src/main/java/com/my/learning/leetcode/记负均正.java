package com.my.learning.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *  首先输入要输入的整数个数n，然后输入n个整数。输出为n个整数中负数的个数，和所有正整数的平均值，结果保留一位小数。
本题有多组输入用例。
输出负数的个数，和所有正整数的平均值。
 * @author guoenjing
 *
 */
public class 记负均正 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String n = scanner.nextLine();
			String[] split = scanner.nextLine().split("\\s+");
			List<Integer> nums = Arrays.stream(split).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
			long nagetiveCount = nums.stream().filter((i)->i<0).count();
			double average = nums.stream().mapToInt(Integer::intValue).filter((i)->i>0).average().getAsDouble();
			String averageFormat = String.format("%.1f", average);
			System.out.println(nagetiveCount+" "+ averageFormat);
		}
		scanner.close();
	}
}
