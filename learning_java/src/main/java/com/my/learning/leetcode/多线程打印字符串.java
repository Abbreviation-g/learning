package com.my.learning.leetcode;

import java.util.*;

//临界资源
class CharBuffer {
	private StringBuffer value = new StringBuffer();// 共享变量
	private int num = 0; // 添加次数
	private int order = 0; // 信号量，约定发送线程的次序

	public String getVal() {
		return value.toString();
	}

	public CharBuffer(int num) {
		this.num = num;
	}

	// 临界区
	public synchronized void put(char c, int order) {
		if (this.order != order) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		value.append(c); // 将字母添加到共享资源
		this.order = (this.order + 1) % 4;
		if (this.order == 0)
			num--;
		this.notifyAll();
	}

	public int getNum() {
		return num;
	}

}

// 发送线程
class SendThread extends Thread {
	private CharBuffer cb;
	private int order = 0; // 信号量，约定发送线程次序
	private char c;

	public SendThread(char c, int order, CharBuffer cb) {
		this.c = c;
		this.order = order;
		this.cb = cb;
	}

	public void run() {
		while (cb.getNum() != 0) {
			cb.put(c, order);
		}
	}
}

public class 多线程打印字符串 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt();
			CharBuffer cb = new CharBuffer(n);
			// 创建线程
			SendThread st1 = new SendThread('A', 0, cb);
			st1.start();
			SendThread st2 = new SendThread('B', 1, cb);
			st2.start();
			SendThread st3 = new SendThread('C', 2, cb);
			st3.start();
			SendThread st4 = new SendThread('D', 3, cb);
			st4.start();
			try {
				// 等待4个线程运行结束
				st1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(cb.getVal());
		}
	}
}
