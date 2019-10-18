package com.my.learning.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarTest {
	public static void main(String[] args) {
		List<Date> allValidDate = new ArrayList<Date>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year  = calendar.get(Calendar.YEAR);
		int month  = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		calendar.set(year, month, date, 0, 0, 0);
		while (calendar.get(Calendar.DATE) == date) {
			allValidDate.add(calendar.getTime());
			calendar.add(Calendar.MINUTE, 5);
		}
		System.out.println(allValidDate.size());
		
		Date date2 = new Date();
		calendar.setTime(date2);
		int minute = calendar.get(Calendar.MINUTE);
		int c = minute%5;
		if(c != 0) {
			
			
		}
		minute += 5-minute%5;
		System.out.println(minute);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date2 = calendar.getTime();
		System.out.println(date2);
	}
}
