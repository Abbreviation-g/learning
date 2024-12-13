package com.my.learning.leetcode;

import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class 四则运算 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
			String line = scanner.nextLine();
			try {
				Object result = engine.eval(line);
				System.out.println(result);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		scanner.close();
	}
}
