package com.my.learning.swing.chartdemo;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ChartDemo19 {
//	实例 162 设置图示位置
	public JFreeChart getJFreeChart() {
		PieDataset dataset = getPieDataset();
		// locale.getISO3Language()
		JFreeChart chart = ChartFactory.createPieChart("2010.8 月份销售排行", dataset, true, true, false);

		return chart;
	}

	/**
	 * 设置饼图使用的字体
	 *
	 * @param chart
	 */
	public void setPiePoltFont(JFreeChart chart) {

		// 图表 (饼图)
		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));

		// 标题
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

		// 图例
		LegendTitle legendTitle = chart.getLegend();
		legendTitle.setItemFont(new Font("宋体", Font.PLAIN, 14));

	}

	/**
	 * 设置图示
	 *
	 * @param chart
	 */
	public void setLegendTitle(JFreeChart chart) {
		LegendTitle legendTitle = chart.getLegend();
		// 设置图示位置
		legendTitle.setPosition(RectangleEdge.RIGHT);
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
		return dataset;
	}

	public static void main(String[] args) {
		ChartDemo19 pieChartDemo1 = new ChartDemo19();
		JFreeChart chart = pieChartDemo1.getJFreeChart();
		pieChartDemo1.setPiePoltFont(chart);
		pieChartDemo1.setLegendTitle(chart);
		ChartFrame chartFrame = new ChartFrame("JFreeChart 的例子", chart);
		chartFrame.pack();
		chartFrame.setVisible(true);

	}
}
