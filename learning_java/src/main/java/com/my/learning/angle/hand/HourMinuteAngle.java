package com.my.learning.angle.hand;

import java.util.Calendar;

public class HourMinuteAngle {
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

			double angleHour = angleHour(hour, minute, second);
			double angleMinute = angleMinute(minute, second);
			// double angleSecond = angleSecond(second);

			double angleHourMinute = angleHand(angleHour, angleMinute);
			if (angleHourMinute == 180) {
				System.out.println("angle between hour and minute is " + angleHourMinute + " degree: " + hour + ":"
						+ minute + ":" + second);
			} else if (angleHourMinute%180 == 90) {
				System.out.println("angle between hour and minute is " + angleHourMinute + " degree: " + hour + ":"
						+ minute + ":" + second);
			}
			// double angleMinuteSecond = angleHand(angleMinute, angleSecond);
		}
	}

	public static double angleSecond(int second) {
		return (second % 60 / 60.0) * 360;
	}

	public static double angleMinute(int minute, int second) {
		return ((minute * 60.0 + second) % (60 * 60) / (60 * 60)) * 360;
	}

	public static double angleHour(int hour, int minute, int second) {
		return (hour * 60 * 60.0 + minute * 60 + second) % (12 * 60 * 60) / (12 * 60 * 60) * 360;
	}

	public static double angleHand(double angle1, double angle2) {
		return Math.abs(angle1 - angle2);
	}
}
