package com.my.learning.proxy;

public class LiuDeHua implements Person {

    public String sing(String name){
        System.out.println("刘德华唱"+name+"歌！！");
        return "歌唱完了，谢谢大家！";
    }
    
    public String dance(String name){
    	beforeDance();
        System.out.println("刘德华跳"+name+"舞！！");
        return "舞跳完了，多谢各位观众！";
    }
    
    private void beforeDance(){
    	System.out.println("LiuDeHua.beforeDance()");
    }
    
    public static void main(String[] args) {
		int i = 1;
		double j = 0.01141;
		System.out.println(i*j);
		System.out.println(j);
	}
}