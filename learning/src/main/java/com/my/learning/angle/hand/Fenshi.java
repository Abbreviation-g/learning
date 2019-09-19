package com.my.learning.angle.hand;

public class Fenshi {
	int fz;
	int fm;

	public Fenshi(int fz, int fm) {
		int g = gcd(fz, fm);
		if (fz % fm == 0) {
			this.fz = fz / fm;
			this.fm = 1;
		} else {
			this.fz = fz / g;
			this.fm = fm / g;
		}
	}

	public Fenshi add(Fenshi fs) {
		int newfz = fz * fs.fm + fm * fs.fz;
		int newfm = fm * fs.fm;
		return new Fenshi(newfz, newfm);
	}

	public Fenshi sub(Fenshi fs) {
		int newfz = fz * fs.fm - fm * fs.fz;
		int newfm = fm * fs.fm;
		return new Fenshi(newfz, newfm);
	}

	public Fenshi subAbs(Fenshi fs) {
		int newFz = Math.abs(fz * fs.fm - fm * fs.fz);
		int newFm = Math.abs(fm * fs.fm);
		return new Fenshi(newFz, newFm);
	}

	public int gcd(int a, int b) { // 求最大公约数
		// 辗转相除法
		int m = Math.max(Math.abs(a), Math.abs(b));
		int n = Math.min(Math.abs(a), Math.abs(b));
		int r;
		while (n != 0) {
			r = m % n;
			m = n;
			n = r;
		}
		return m;
	}

	public String toString() { // 定义输出格式
		if (fz % fm == 0) {
			return fz / fm + "";
		} else {
			return fz + "/" + fm;
		}
	}

	public static void main(String[] args) {
		System.out.println(new Fenshi(3, 6).add(new Fenshi(4, 8)));
	}
}
