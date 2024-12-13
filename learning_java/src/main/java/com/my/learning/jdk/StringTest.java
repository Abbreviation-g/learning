package com.my.learning.jdk;

import java.util.Arrays;

public class StringTest {
	private static String getClassName(String projectName) {
		String className = projectName;
		className = className.replaceAll("[^a-zA-Z0-9-_]*", "");
		String[] segments = className.split("-|_");
		className = Arrays.stream(segments).map((s) -> upperFirstCharacter(s)).reduce((s1,s2)->s1+s2).get();
		return className;
	}

	private static String upperFirstCharacter(String str) {
		if (str.length() == 0) {
			return str;
		} else if (str.length() == 1) {
			return str.toUpperCase();
		}
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static void main(String[] args) {
		String projectName = "this_is_a-class";
		System.out.println(getClassName(projectName));
	}
}
