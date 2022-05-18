package com.my.learning.leetcode;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * 题目描述

数据表记录包含表索引和数值（int范围的正整数），请对表索引相同的记录进行合并，即将相同索引的数值进行求和运算，输出按照key值升序进行输出。
输入描述:

先输入键值对的个数
然后输入成对的index和value值，以空格隔开

输出描述:

输出合并后的键值对（多行）

示例1
输入
复制

4
0 1
0 2
1 2
3 4

输出
复制

0 3
1 2
3 4


 * @author guoenjing
 *
 */
public class 合并表记录 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int numOfEntry = scanner.nextInt();
		scanner.nextLine();
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = 0; i < numOfEntry; i++) {
			String entryLine = scanner.nextLine().trim();
			String[] split = entryLine.split("\\s+");
			if(split.length != 2) {
				continue;
			}
			int key = Integer.parseInt(split[0]);
			int value = Integer.parseInt(split[1]);
			if(map.containsKey(key)) {
				map.put(key, map.get(key) + value);
			} else {
				map.put(key, value);
			}
		}
		
		scanner.close();
		
		Set<Entry<Integer,Integer>> entrySet = map.entrySet();
		for (Entry<Integer, Integer> entry : entrySet) {
			String line = entry.getKey()+" "+entry.getValue();
			System.out.println(line);
		}
	}
}
