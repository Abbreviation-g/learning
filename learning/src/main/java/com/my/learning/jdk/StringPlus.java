package com.my.learning.jdk;

import org.apache.bcel.generic.NEW;

public class StringPlus {
	public static void main(String[] args) {
		String s1 = "a";
		String s2 = "b";
		System.out.println(s1+s2);
		
		new StringBuilder(String.valueOf(s1)).append(s2).toString();
//		
	}
}
