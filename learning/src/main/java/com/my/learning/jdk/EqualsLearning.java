package com.my.learning.jdk;

import java.lang.reflect.Field;

public class EqualsLearning {
	public static void main(String[] args) throws Exception  {

    	Integer a = 1,b = 2;
    	swap(a,b);
    	System.out.println("a:  "+a);
    	System.out.println("b:  "+b);
    	System.out.println(a==b);
    	Integer a1 = 100;
    	Integer b1 = 100;
    	System.out.println(a1==b1);
    	Integer a2 = 128;
    	Integer b2 = 128;
    	System.out.println(a2==b2);
    }

	private static void swap(Integer a, Integer b) throws Exception {
		Field field = Integer.class.getDeclaredField("value");
		field.setAccessible(true);
		int tmp = a.intValue(); // 1
		field.set(a, b.intValue());// 1

		System.out.println(Integer.valueOf(tmp));
		System.out.println(Integer.valueOf(1));
		field.set(b, tmp);
		
	}

}
