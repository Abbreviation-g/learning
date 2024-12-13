package com.my.learning.parser;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.eval.Variable;
import parsii.tokenizer.ParseException;

public class PasiiTest {
	public static void main(String[] args) throws ParseException {
		Scope scope = Scope.create();   
		Expression expr = Parser.parse("num++==3", scope);
		
//		Expression expr = Parser.parse("3 + a * 4", scope);   
//		a.setValue(4);   
//		System.out.println(expr.evaluate());   
//		a.setValue(5);   
//		System.out.println(expr.evaluate());
		
	}
}
