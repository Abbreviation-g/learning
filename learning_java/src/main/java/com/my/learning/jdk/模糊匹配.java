package com.my.learning.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 模糊匹配 {
	public static List search(String name,List<String> list){
		   List results = new ArrayList();
		   Pattern pattern = Pattern.compile(name);
		   for(int i=0; i < list.size(); i++){
		      Matcher matcher = pattern.matcher(list.get(i));
		      if(matcher.find()){
		         results.add(list.get(i));
		      }
		   }
		   return results;
		}
	
	public static void main(String[] args) {
		List<String> list= new ArrayList<String>();
		list.add("aa");
		list.add("ab");
		list.add("ac");
		list.add("ba");
		System.out.println(search("b", list));
	}
}
