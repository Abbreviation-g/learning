package com.my.learning.annotation;

import java.lang.annotation.Annotation;

public class Test {
	public static class t{
		
	}
	
	
	public static void main(String[] args) {
		Class<? extends Source> clazz = Source2.class;
//		for (Method method : clazz.getDeclaredMethods()) {
//			System.out.println(method.getName());
//			Annotation[] annotations = method.getDeclaredAnnotations();
//			System.out.println(Arrays.toString(annotations));
//			System.out.println("----");
//		}
//		System.out.println("------------" + "GetAnnotation.main()" + "------------");
//		for (Method method : clazz.getMethods()) {
//			System.out.println(method.getName());
//			Annotation[] annotations = method.getAnnotations();
//			System.out.println(Arrays.toString(annotations));
//			System.out.println("----");
//		}
		for (Annotation annotation : clazz.getAnnotations()) {
			System.out.println(annotation);
		}
	}
}
