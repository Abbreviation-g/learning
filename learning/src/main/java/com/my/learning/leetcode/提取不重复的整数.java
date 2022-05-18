package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 题目描述
输入一个int型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。
保证输入的整数最后一位不是0。
输入描述:

输入一个int型整数

输出描述:

按照从右向左的阅读顺序，返回一个不含重复数字的新的整数

示例1
输入
复制

9876673

输出
复制

37689


 * @author guoenjing
 *
 */
public class 提取不重复的整数 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		String line = scanner.nextLine();
		char[] charArray = line.toCharArray();
		Map<Integer,Integer> map = new LinkedHashMap<>();
		for (int i = charArray.length -1 ; i >=0; i--) {
			int num = Integer.parseInt(Character.toString(charArray[i]));
			if(!map.containsKey(num)) {
				map.put(num	, 0);
			}
		}
		Set<Integer> keySet = map.keySet();
		String string = keySet.stream().map((i)->Integer.toString(i)).reduce((i1,i2)->{return i1+i2;}).get();
		System.out.println(string);
		scanner.close();
	}
}
