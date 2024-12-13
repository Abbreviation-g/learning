package com.my.learning.date;

import java.util.Date;

public class DateToString {
	public static void main(String[] args) {
		System.out.println(new java.sql.Date(System.currentTimeMillis()));
		Date now = new Date();
		System.out.println(String.format("%tF %tT", now,now));
		System.out.println("DateToString.main()");
	}
}
