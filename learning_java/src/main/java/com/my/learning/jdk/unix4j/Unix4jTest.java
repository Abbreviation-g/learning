package com.my.learning.jdk.unix4j;

import java.io.File;

import org.unix4j.Unix4j;
import org.unix4j.builder.Unix4jCommandBuilder;

public class Unix4jTest {
	public static void main(String[] args) {
//		Tuesday kilogram kilogram kg 
//		Tuesday kilogram kilogram kg
//		Tuesday kilogram kilogram kg
		Unix4jCommandBuilder unix4j = Unix4j.cat(Unix4j.class.getResource("/unix4j-sed.text").getFile())
				./* grep("Tuesday"). */sed("s/kilogram/kg/g").sed("s/kg/千克/g").sort();
		String resultString = unix4j.toStringResult();
		System.out.println(resultString);
		
		String context = "Tuesday kilogram kilogram kg \r\n" + 
				"Tuesday kilogram kilogram kg\r\n" + 
				"Tuesday kilogram kilogram kg\r\n" + 
				"";
		unix4j = Unix4j.fromString(context).sed("s/kilogram/kg/g").sed("s/kg/公斤/g");
		resultString = unix4j.toStringResult();
		System.out.println(resultString);
	}
}
