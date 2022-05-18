package com.my.learning.leetcode;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 编写一个程序，将输入字符串中的字符按如下规则排序。 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。 如，输入： Type 输出： epTy 规则
 * 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。 如，输入： BabA 输出： aABb 规则 3 ：非英文字母的其它字符保持原来的位置。
 * 如，输入： By?e 输出： Be?y
 * 
 * A Famous Saying: Much Ado About Nothing (2012/8). 
 * A aaAAbc dFgghh: iimM nNn oooos Sttuuuy (2012/8).
 * 
 * @author guoenjing
 *
 */
public class 字符串排序 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String newline = sort(line);
			System.out.println(newline);
			
		}
		scanner.close();
	}

	public static String sort(String line) {
		char[] chars = line.toCharArray();
		char[] sortChars = new char[chars.length];

		List<Character> letters = new LinkedList<Character>();

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (Character.isLetter(ch)) {
				letters.add(ch);
			} else {
				sortChars[i] = ch;
			}
		}
		Collections.sort(letters, new LetterComparator());
		for (int i = 0, j = 0; i < sortChars.length && j < letters.size(); i++) {
			if (sortChars[i] == '\u0000') {
				sortChars[i] = letters.get(j);
				j++;
			}
		}
		String newLine = new String(sortChars);
		return newLine;
	}

	/**
	 * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。 如，输入： Type 输出： epTy 规则 2
	 * ：同一个英文字母的大小写同时存在时，按照输入顺序排列。 如，输入： BabA 输出： aABb 规则 3 ：非英文字母的其它字符保持原来的位置。
	 * 如，输入： By?e 输出： Be?y
	 * 
	 * @author guoenjing
	 *
	 */
	private static class LetterComparator implements Comparator<Character> {

		@Override
		public int compare(Character o1, Character o2) {
			// 同一个英文字母的大小写同时存在时，按照输入顺序排列。
			if (Character.toLowerCase(o1) == Character.toLowerCase(o2)) {
				return 0;
			}
			// 英文字母从 A 到 Z 排列，不区分大小写。
			return Character.compare(Character.toLowerCase(o1), Character.toLowerCase(o2));
		}

	}
}
