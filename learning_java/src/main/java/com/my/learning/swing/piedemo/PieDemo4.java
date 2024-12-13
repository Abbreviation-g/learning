package com.my.learning.swing.piedemo;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

public class PieDemo4 extends ApplicationFrame {
//	实例 169 饼图旋转角度
	public PieDemo4(String title) {
		super(title);
	}

	/**
	 * 创建一个饼图表的数据集
	 *
	 * @return
	 */
	private PieDataset getPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("JAVA 从入门到精通（第 2 版）", 500);
		dataset.setValue("视频学 JAVA", 800);
		dataset.setValue("JAVA 全能速查宝典", 1000);
		dataset.setValue("Java 范例完全自学手册 (1DVD)", 400);
		dataset.setValue("Java 开发典型模块大全", 750);
		return dataset;
	}

	/**
	 * 生成 JFreeChart
	 *
	 * @return
	 */
	private JFreeChart getJFreeChart() {
		PieDataset dataset = getPieDataset();
		JFreeChart chart = ChartFactory.createPieChart("2010.8 月份销售排行", dataset, true, true, false);
		setPiePoltFont(chart);
		return chart;
	}

	/**
	 * 设置饼图使用的字体
	 *
	 * @param chart
	 */
	protected void setPiePoltFont(JFreeChart chart) {

		// 图表 (饼图)
		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));

		// 标题
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

		// 图例
		LegendTitle legendTitle = chart.getLegend();
		legendTitle.setItemFont(new Font("宋体", Font.PLAIN, 14));

	}

	/**
	 * 设置 Pie
	 *
	 * @param chart
	 */
	public void createPiePlot() {
		JFreeChart chart = getJFreeChart();
		PiePlot piePlot = (PiePlot) chart.getPlot();
		// 设置旋转角度
		piePlot.setStartAngle(120);

		// 把 JFreeChart 面板保存在窗体里
		setContentPane(new ChartPanel(chart));

	}

	public static void main(String[] args) {
		PieDemo4 pieChartDemo1 = new PieDemo4("饼图实例");
		pieChartDemo1.createPiePlot();
		pieChartDemo1.pack();
		// 把窗体显示到显示器中央
		RefineryUtilities.centerFrameOnScreen(pieChartDemo1);
		pieChartDemo1.setVisible(true);

	}
}
