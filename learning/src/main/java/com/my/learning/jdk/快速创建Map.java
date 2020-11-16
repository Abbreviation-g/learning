package com.my.learning.jdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class 快速创建Map {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			{
				put("1","1");
			}
		};
		System.out.println(map);
		
		System.out.println(new TypeClazz());
	}
	
	static class TypeClazz {
		static Set<String > set = new HashSet<>();
		static {
			set = new TreeSet<String>();
		}
		
		List<String> list = new ArrayList<>();
		public TypeClazz() {
			System.out.println("快速创建Map.TypeClazz.TypeClazz()");
		}
		
		{
			System.out.println("快速创建Map.TypeClazz.enclosing_method()");
		}
	}
}
