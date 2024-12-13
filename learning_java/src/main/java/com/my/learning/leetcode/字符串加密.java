package com.my.learning.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *  有一种技巧可以对数据进行加密，它使用一个单词作为它的密匙。下面是它的工作原理：
 *  首先，选择一个单词作为密匙，如TRAILBLAZERS。如果单词中包含有重复的字母，只保留第1个，其余几个丢弃。现在，修改过的那个单词属于字母表的下面，如下所示：
A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
T R A I L B Z E S C D F G H J K M N O P Q U V W X Y
上面其他用字母表中剩余的字母填充完整。
在对信息进行加密时，信息中的每个字母被固定于顶上那行，并用下面那行的对应字母一一取代原文的字母(字母字符的大小写状态应该保留)。
因此，使用这个密匙，Attack AT DAWN(黎明时攻击)就会被加密为Tpptad TP ITVH。
请实现下述接口，通过指定的密匙和明文得到密文。
 
 * @author guoenjing
 *
 */
public class 字符串加密 {
	public static void main(String[] args) {
		
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String key = scanner.nextLine();
			String word = scanner.nextLine();
			
			System.out.println(encrypt(key, word));
			
		}
		scanner.close();
	}
	
	private static String encrypt(String key, String word) {
		key = key.toUpperCase();
		// A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
		String[] split = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split("\\s+");
		List<Character> letterList = Arrays.stream(split).map(s->s.charAt(0)).collect(Collectors.toList());
		List<Character> newLetterList = new ArrayList<Character>();
		
		List<Character> letterListTemp = new ArrayList<>(letterList);
		
		
		// 先对key去重
		char[] keyArr = key.toCharArray();
		for(char c: keyArr) {
			if(!newLetterList.contains(c)) {
				newLetterList.add(c);
			}
			letterListTemp.remove(Character.valueOf(c));
		}
		newLetterList.addAll(letterListTemp);
		
		List<Character> newWordCharList = new ArrayList<>();
		for(char ch: word.toCharArray()) {
			int indexOf = letterList.indexOf(Character.toUpperCase(Character.valueOf(ch)));
			if(indexOf == -1) {
				newWordCharList.add(ch);
				continue;
			}
			char newChar = newLetterList.get(indexOf);
			if(Character.isLowerCase(ch)) {
				newChar = Character.toLowerCase(newChar);
			}
			newWordCharList.add(newChar);
		}
		StringBuilder result = new StringBuilder();
		for (Character character : newWordCharList) {
			result.append(character);
		}
		return result.toString();
	}
}
