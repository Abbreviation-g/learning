package com.my.learning.jdk;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetGenericType {
	static List<? extends Object  > list = new ArrayList<String>();
//	static Map<String, Integer> map;
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		getGenericType(GetGenericType.class);
	}
	
	static void getGenericType(Class<?> clazz) throws NoSuchFieldException, SecurityException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Type type = field.getGenericType();
			if(ParameterizedType.class.isAssignableFrom(type.getClass())) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				System.out.println(parameterizedType);// java.util.List<java.lang.String>
				for(Type type2 : parameterizedType.getActualTypeArguments()) {
					if(WildcardType.class.isAssignableFrom(type2.getClass())) {
						WildcardType wildcardType = (WildcardType) type2;
						System.out.println(wildcardType+", " + "extends: " + Arrays.toString(wildcardType.getUpperBounds()) +", super: " + Arrays.toString(wildcardType.getLowerBounds()));
					} else {
						System.out.println(type2);
					}
				}
			}
		}
	}
}
