package com.my.learning.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(value= {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestMethodAnnotation {
	public String string() default "";
	
	public String[] strings() default {};
	
	public ElementType enumType() default ElementType.METHOD;
	
	public Retention annotation() default @Retention(RetentionPolicy.RUNTIME);
}
