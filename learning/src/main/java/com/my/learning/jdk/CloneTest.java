package com.my.learning.jdk;

import java.util.Arrays;

/**
 * 深层拷贝案例
 * @author guo
 *
 */
public class CloneTest {
	public static class ObjectA implements Cloneable{
		private String a;
		private int[] array;
		private Container container;
		@Override
		public Object clone() throws CloneNotSupportedException {
			ObjectA oa =  (ObjectA) super.clone();
			oa.setArray(getArray().clone());
			oa.setContainer((Container) getContainer().clone());
			return oa;
		}
		public String getA() {
			return a;
		}
		public void setA(String a) {
			this.a = a;
		}
		public int[] getArray() {
			return array;
		}
		public void setArray(int[] array) {
			this.array = array;
		}
		public void setContainer(Container container) {
			this.container = container;
		}
		public Container getContainer() {
			return container;
		}
	}
	
	public static class Container implements Cloneable{
		private String context;
		public Container(String context) {
			this.context = context;
		}
		public String getContext() {
			return context;
		}
		@Override
		public String toString() {
			return "{"+hashCode()+", "+getContext()+"}";
		}
		@Override
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		ObjectA oa = new ObjectA();
		oa.setA("str_a");
		oa.setArray(new int[] {0,1,2});
		oa.setContainer(new Container("container_a"));
		System.out.println("{"+oa.getA().hashCode()+","+oa.getA()+"}"+", "+"{"+oa.getArray().hashCode()+", "+Arrays.toString(oa.getArray())+"}"+", "+oa.getContainer()+": "+oa);
		ObjectA ob = (ObjectA) oa.clone();
		System.out.println("{"+ob.getA().hashCode()+","+ob.getA()+"}"+", "+"{"+ob.getArray().hashCode()+", "+Arrays.toString(ob.getArray())+"}"+", "+ob.getContainer()+": "+ob);
	}
}
