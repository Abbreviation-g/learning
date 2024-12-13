package com.my.learning.angle.hand;

import java.util.Calendar;

public class HourMinuteAngle2 {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		for (int i = 0; i < 12 * 60 * 60; i++) {
			calendar.add(Calendar.SECOND, 1);
			int hour = calendar.get(Calendar.HOUR);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);

			Fenshi angleHour = angleHour(hour, minute, second);
			Fenshi angleMinute = angleMinute(minute, second);
			// double angleSecond = angleSecond(second);

			Fenshi angleHourMinute = angleHand(angleHour, angleMinute);
			if (angleHourMinute.toString().equals(Integer.toString(180))) {
				System.out.println("angleHourMinute->" + angleHourMinute + "->" + hour + ":" + minute + ":" + second);
			} else if (angleHourMinute.toString().equals(Integer.toString(90))) {
				System.out.println("angleHourMinute->" + angleHourMinute + "->" + hour + ":" + minute + ":" + second);
			} else if (angleHourMinute.toString().equals(Integer.toString(270))) {
				System.out.println("angleHourMinute->" + angleHourMinute + "->" + hour + ":" + minute + ":" + second);
			}
		}
	}

	public static Fenshi angleSecond(int second) {
		int fenzi = second % 60 * 360;
		int fenmu = 60;
		return new Fenshi(fenzi, fenmu);
	}

	public static Fenshi angleMinute(int minute, int second) {
		int fenzi = (minute * 60 + second) % (60 * 60) * 360;
		int fenmu = 60 * 60;

		return new Fenshi(fenzi, fenmu);
	}

	public static Fenshi angleHour(int hour, int minute, int second) {
		int fenzi = (hour * 60 * 60 + minute * 60 + second) % (12 * 60 * 60) * 360;
		int fenmu = 12 * 60 * 60;

		return new Fenshi(fenzi, fenmu);
	}

	public static Fenshi angleHand(Fenshi angle1, Fenshi angle2) {
		return angle1.subAbs(angle2);
	}
}
