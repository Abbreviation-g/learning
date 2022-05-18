package com.my.learning.leetcode;

import java.util.Scanner;

/**
 * 原理：ip地址的每段可以看成是一个0-255的整数，把每段拆分成一个二进制形式组合起来，然后把这个二进制数转变成 一个长整数。
 * 举例：一个ip地址为10.0.3.193 每段数字 相对应的二进制数 10 00001010 0 00000000 3 00000011 193
 * 11000001 组合起来即为：00001010 00000000 00000011
 * 11000001,转换为10进制数就是：167773121，即该IP地址转换后的数字就是它了。
 * 
 * 本题含有多组输入用例，每组用例需要你将一个ip地址转换为整数、将一个整数转换为ip地址。
 * 
 * 输入 10.0.3.193 167969729 输出 167773121 10.3.3.193
 * 
 * @author guoenjing
 *
 */
public class 整数和IP地址的转换 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim();
			if (isIP(line)) {
				String ipToNum = ipToNum(line);
				System.out.println(ipToNum);
			} else {
				String numToIP = numToIP(line);
				System.out.println(numToIP);
			}
		}
		scanner.close();
	}

	private static boolean isIP(String line) {
		if (line.indexOf('.') > 0) {
			return true;
		}
		return false;
	}

	private static String ipToNum(String ip) {
		String binarys = new String();
		String[] split = ip.split("\\.");
		for (String ipPart : split) {
			int parseInt = Integer.parseInt(ipPart);
			String binaryString = Integer.toBinaryString(parseInt);
			int length = binaryString.length();
			for(int i =0 ;i<8-length;i++) {
				binaryString = '0'+binaryString;
			}
			binarys = binarys+binaryString;
		}
		long parseInt = Long.parseLong(binarys, 2);
		return Long.toString(parseInt);
	}

	private static String numToIP(String num) {
		long numInt=Long.parseLong(num);
		String binaryString = Long.toBinaryString(numInt);
		int length = binaryString.length();
		for (int i = 0; i < 32-length; i++) {
			binaryString = '0' +binaryString;
		}
		StringBuilder result = new StringBuilder();
		int beginIndex = 0;
		while(binaryString.length() >0) {
			String str = binaryString.substring(beginIndex, beginIndex+8);
			binaryString = binaryString.substring(beginIndex+8);
			int parseInt = Integer.parseInt(str,2);
			result.append(parseInt);
			result.append('.');
		}
		String string = result.toString();
		return string.substring(0, string.length()-1);
	}
}
