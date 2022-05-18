package com.my.learning.leetcode;

import java.util.Scanner;

public class 多线程打印字符串my {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int nextInt = scanner.nextInt();
			scanner.nextLine();
			
			StringResult result = new StringResult(nextInt);
			PrintThread th1= new PrintThread('A', 0, result);
			PrintThread th2= new PrintThread('B', 1, result);
			PrintThread th3= new PrintThread('C', 2, result);
			PrintThread th4= new PrintThread('D', 3, result);
			
			th1.start();
			th2.start();
			th3.start();
			th4.start();
			
			try {
				th1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(result.getResult().toString());
		}
		scanner.close();
	}
	
	private static class PrintThread extends Thread{
		private char ch;private int threadNum;private StringResult result;
		public PrintThread(char ch, int threadNum,StringResult result) {
			this.ch = ch;
			this.threadNum = threadNum;
			this.result = result;
		}
		
		@Override
		public void run() {
			while(result.getCount() >0) {
				result.printLetter(ch, threadNum);
			}
		}
	}
	
	private static class StringResult{
		private StringBuffer result = new StringBuffer(); 
		private int count;
		private int nextThreadNum;
		public StringResult(int count) {
			this.count = count;
		}
		
		public synchronized void printLetter(char ch, int threadNum) {
			if(threadNum != nextThreadNum) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			nextThreadNum = (threadNum+1)%4;
			if(nextThreadNum == 0) {
				count --;
			}
			result.append(ch);
			this.notifyAll();
		}
		
		public int getCount() {
			return count;
		}
		
		public StringBuffer getResult() {
			return result;
		}
	}
}
