package com.my.learning.annotation;

//@TestMethodAnnotation
public class Source2 extends Source {
	
	@Override
	@TestMethodAnnotation2
	public void getPublic() {
		super.getPublic();
	}
}
