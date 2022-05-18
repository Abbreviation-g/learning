package com.my.learning.leetcode;

import java.util.Scanner;

/**
 * 考试题目和要点：
 * 1、中文大写金额数字前应标明“人民币”字样。中文大写金额数字应用壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿、元、角、分、零、整等字样填写。
 * 2、中文大写金额数字到“元”为止的，在“元”之后，应写“整字，如532.00应写成“人民币伍佰叁拾贰元整”。在”角“和”分“后面不写”整字。
 * 3、阿拉伯数字中间有“0”时，中文大写要写“零”字，阿拉伯数字中间连续有几个“0”时，中文大写金额中间只写一个“零”字，如6007.14，应写成“人民币陆仟零柒元壹角肆分“。
 * 4、10应写作“拾”，100应写作“壹佰”。例如，1010.00应写作“人民币壹仟零拾元整”，110.00应写作“人民币壹佰拾元整”
 * 5、十万以上的数字接千不用加“零”，例如，30105000.00应写作“人民币叁仟零拾万伍仟元整” input 151121.15 10012.02
 * output 人民币拾伍万壹仟壹佰贰拾壹元壹角伍分 人民币壹万零拾贰元贰分
 * 
 * @author guoenjing
 *
 */
public class 人民币转换 {
	static String[] gewei = new String[] {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
	public static void main(String[] args) {
		// 壹、贰、叁、肆、伍、陆、柒、捌、玖
		// 拾、佰、仟、万、亿
		String[] gaowei = new String[] {"拾","佰","仟","万","亿"};
		
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			double d = scanner.nextDouble();
			scanner.nextLine();
			System.out.println(formatRMB(d));
		}
		scanner.close();
	}

	public static String formatRMB(double input) {
		StringBuilder result = new StringBuilder("人民币");
		String doubleStr = Double.toString(input);

		int index = doubleStr.indexOf('.');
		String zhengshuStr = doubleStr.substring(0, index);
		String xiaoshuStr = doubleStr.substring(index + 1, doubleStr.length());
		long zhengshu = Long.parseLong(zhengshuStr);
		int xiaoshu = Integer.parseInt(xiaoshuStr);
		
		result.append(formatZhengshu(zhengshu));
		result.append(formatXiaoshu(xiaoshu));

		return result.toString();
	}

	private static String formatZhengshu(long zhengshu) {
		final int WAN = 10000;
		final int YI = WAN*WAN;
		
		int yi4 = (int)zhengshu / YI;
		int wan4 = (int)(zhengshu - yi4 *YI )/WAN;
		int ge4 = (int)(zhengshu - yi4*YI - wan4*WAN) ;
		
		StringBuilder result = new StringBuilder();
		if(yi4 != 0) {
			result.append(format0(yi4));
			result.append("亿");
		}
		if(wan4 != 0 ) {
			result.append(format0(wan4));
			result.append("万");
		}
		if(ge4 != 0) {
			result.append(format0(ge4));
			result.append("元");
		}
		
		
		return result.toString();
	}
	
	private static String format0(int num4) {
		StringBuilder result = new StringBuilder();
		int qian = num4 / 1000;
		int bai = (num4 - qian *1000) /100;
		int shi  = (num4 - qian*1000 - bai*100)/10;
		int ge = (num4 - qian*1000 - bai*100 - shi*10);
		if(qian != 0) {
			result.append(gewei[qian]);
		}
		if (bai != 0) {
			result.append(gewei[bai] + "佰");
		} else if (qian != 0 && bai == 0 && (shi != 0 || ge != 0)) {
			result.append("零");
		}
		if (shi != 0 && shi != 1) {
			result.append(gewei[shi] + "拾");
		} else if (bai != 0 && ge != 0) {
			result.append("零");
		}
		if (shi == 1 && qian == 0 && bai == 0) {
			result.append("拾");
		}

		if (ge != 0) {
			result.append(gewei[ge]);
		}
		return result.toString();
	}

	private static String formatXiaoshu(int xiaoshu) {
		StringBuilder result = new StringBuilder();
		int jiao = xiaoshu / 10;
		int fen = xiaoshu % 10;
		if (jiao != 0 && fen != 0) {
			result.append(gewei[jiao] + "角" + gewei[fen] + "分");
		} else if (jiao != 0 && fen == 0) {
			result.append(gewei[jiao] + "角");
		} else if (jiao == 0 && fen != 0) {
			result.append("零" + gewei[fen] + "分");
		} else if (jiao == 0 && fen == 0) {
			result.append("整");
		}
		return result.toString();
	}
}
