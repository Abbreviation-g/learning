package com.my.learning.parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.BaseExpression;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.lexer.SymbolTable;
import com.googlecode.aviator.lexer.token.Variable;

public class AviatorTest {
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        
        String expression = "(num =3) >100";
//        expression = "f1() && f2()";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        System.out.println(compiledExp.getClass());
        System.out.println(compiledExp.getVariableNames());
        
        BaseExpression baseExpression = (BaseExpression) compiledExp;
        Field field = BaseExpression.class.getDeclaredField("symbolTable");
        field.setAccessible(true);
        SymbolTable object = (SymbolTable) field.get(baseExpression);
        Field tableField = SymbolTable.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Map<String, Variable> table = (Map<String, Variable>) tableField.get(object);
        System.out.println(table);
//        env = new HashMap<String, Object>();
//        env.put("a", 100.3);
//        env.put("b", 45);
//        env.put("c", -199.100);
//        // 执行表达式
//        Boolean result2 = (Boolean) compiledExp.execute(env);
//        System.out.println(result2);  // false
	}
	
	class MyExpression extends BaseExpression{

		
		public MyExpression(AviatorEvaluatorInstance instance, List<String> varNames, SymbolTable symbolTable) {
			super(instance, varNames, symbolTable);
		}

		@Override
		public Object executeDirectly(Map<String, Object> env) {
			return null;
		}
		
	}
}
