package com.my.learning.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  密码是我们生活中非常重要的东东，我们的那么一点不能说的秘密就全靠它了。哇哈哈. 接下来渊子要在密码之上再加一套密码，虽然简单但也安全。
假设渊子原来一个BBS上的密码为zvbo9441987,为了方便记忆，他通过一种算法把这个密码变换成YUANzhi1987，这个密码是他的名字和出生年份，怎么忘都忘不了，而且可以明目张胆地放在显眼的地方而不被别人知道真正的密码。
他是这么变换的，大家都知道手机上的字母： 1--1， abc--2, def--3, ghi--4, jkl--5, mno--6, pqrs--7, tuv--8 wxyz--9, 0--0,就这么简单，渊子把密码中出现的小写字母都变成对应的数字，数字和其他的符号都不做变换，
声明：密码中没有空格，而密码中出现的大写字母则变成小写之后往后移一位，如：X，先变成小写，再往后移一位，不就是y了嘛，简单吧。记住，z往后移是a哦。

 输入包括多个测试数据。输入是一个明文，密码长度不超过100个字符，输入直到文件结尾
 
 输出渊子真正的密文
 * @author guoenjing
 *
 */
public class 简单密码 {
	/**
	 * 
YUANzhi1987 明文
zvbo9441987 输出渊子真正的密文
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String nextLine = scanner.nextLine();
		scanner.close();
		
		System.out.println(password(nextLine));
	}
	// 1--1， abc--2, def--3, ghi--4, jkl--5, mno--6, pqrs--7, tuv--8 wxyz--9, 0--0
	
	public static String password(String input) {
		
		
		StringBuilder result = new StringBuilder();
		char[] charArray = input.toCharArray();
		for (char ch : charArray) {
			if(ch >= 'a' && ch <= 'z') {
				result.append(lowerCaseHandler(ch));
			}else if(ch>='A' && ch <= 'Z') {
				result.append(uperCaseHandler(ch));
			} else {
				result.append(ch);
			}
		}
		
		return result.toString();
	}
	
	public static String uperCaseHandler(char ch) {
		ch = Character.toLowerCase(ch);
		if(ch == 'z') {
			ch = 'a';
		} else {
			ch +=1;
		}
		return Character.toString(ch);
	}
	
	public static String lowerCaseHandler(char ch) {
		// 处理小写字符
				Map<Character,	 Integer> lowerCaseMap = new HashMap<Character, Integer>(8);
				String lowerCaseArr = "abc--2, def--3, ghi--4, jkl--5, mno--6, pqrs--7, tuv--8, wxyz--9";
				String[] split = lowerCaseArr.split(",\\s+");
				for (String keyValue : split) {
					String[] split2 = keyValue.split("--");
					String key = split2[0];
					String value = split2[1];
					for(char k: key.toCharArray()) {
						lowerCaseMap.put(k, Integer.parseInt(value));
					}
				}
				return lowerCaseMap.get(ch).toString();
	}
}
