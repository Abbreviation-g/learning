package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * 
 定义一个单词的“兄弟单词”为：交换该单词字母顺序，而不添加、删除、修改原有的字母就能生成的单词。
兄弟单词要求和原来的单词不同。例如：ab和ba是兄弟单词。ab和ab则不是兄弟单词。
现在给定你n个单词，另外再给你一个单词str，让你寻找str的兄弟单词里，字典序第k大的那个单词是什么？
注意：字典中可能有重复单词。本题含有多组输入数据。 
3 abc bca cab abc 1
2
bca
 * @author guoenjing
 *
 */
public class 查找兄弟单词 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		String[] split = line.split("\\s+");
		int wordCount = Integer.parseInt(split[0]);
		List<String> words = new ArrayList<String>();
		for (int i = 1; i < wordCount+1; i++) {
			words.add(split[i]);
		}
		String str = split[1+wordCount];
		Integer k = Integer.parseInt(split[2+wordCount]);
		
		ListIterator<String> listIterator = words.listIterator();
		while(listIterator.hasNext()) {
			String word = listIterator.next(); 
			if(!check(word, str)) {
				listIterator.remove();
			}
		}
		Collections.sort(words);
		System.out.println(words.size());
		System.out.println(words);
		if(k < words.size()) {
			System.out.println(words.get(k-1));
		}
		scanner.close();
	}
	
	private static boolean check(String word, String str) {
		if(word.equals(str)) {
			return false;
		}
 		char[] wordChs = word.toCharArray();
		Arrays.sort(wordChs);
		char[] strChs = str.toCharArray();
		Arrays.sort(strChs);
		
		return Arrays.equals(wordChs, strChs);
	}
}
