package com.my.learning.leetcode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  实现删除字符串中出现次数最少的字符，若多个字符出现次数一样，则都删除。输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。
注意每个输入文件有多组输入，即多个字符串用回车隔开
字符串只包含小写英文字母, 不考虑非法输入，输入的字符串长度小于等于20个字节。
abcdd
aabcddd
删除字符串中出现次数最少的字符后的字符串。
dd
aaddd
 * @author guoenjing
 *
 */
public class 删除字符串中出现次数最少的字符 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String  line = scanner.nextLine();
			String newLine = deleteStr(line);
			System.out.println(newLine);
		}
		
		scanner.close();
	}
	
	private static String deleteStr(String line) {
		char[] charArray = line.toCharArray();
		Map<Character, Integer> charMap = new LinkedHashMap<>();
		for (char ch : charArray) {
			int count = 0;
			if(charMap.containsKey(ch)) {
				count = charMap.get(ch);
			}
			count++;
			charMap.put(ch, count);
		}
		Integer leastCount = Collections.min(charMap.values());
		
		StringBuilder resule = new StringBuilder();
		for(char ch: charArray ) {
			if(charMap.get(ch) == leastCount) {
				continue;
			}
			resule.append(ch);
		}
		return resule.toString();
	}
}
