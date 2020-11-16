package com.my.learning.annotation;

import java.lang.annotation.Retention;

@TestMethodAnnotation()
public class Source {
	
	@TestMethodAnnotation
	private void getPrivate() {
		
	}
	@TestMethodAnnotation
	public void getPublic() {
		
	}
	
	public static void main(String[] args) {
		
	}
}
