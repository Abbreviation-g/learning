package com.my.learning.swing.piedemo;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

public class PieDemo10 extends ApplicationFrame {
//	实例 176 顺时针旋转 3D 饼图
	public PieDemo10(String title) {
		super(title);
	}

	/**
	 * 创建一个饼图表的数据集 把数据添加到数据集中
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
	 * 获取数据集，生成 JFreeChart，
	 *
	 * @return
	 */
	private JFreeChart getJFreeChart() {
		PieDataset dataset = getPieDataset();
		JFreeChart chart = ChartFactory.createPieChart3D("2010.8 月份销售排行", dataset, true, true, false);
		// 设置饼图使用的字体
		setPiePoltFont(chart);
		return chart;
	}

	/**
	 * 设置饼图使用的字体
	 *
	 * @param chart
	 */
	protected void setPiePoltFont(JFreeChart chart) {

		// 分类标签字体和显示
		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));

		// 标题字体
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

		// 图例字体
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
		PiePlot3D plot = (PiePlot3D) chart.getPlot();

		ActionListener actionListener = new PieDemo10Listener(plot);
		// 添加监听
		Timer timer = new Timer(100, actionListener);
		// 启动 timer 时间器
		timer.start();

		// 把 JFreeChart 对象保存到面板中
		setContentPane(new ChartPanel(chart));

	}

	public static void main(String[] args) {
		PieDemo10 pieChart = new PieDemo10("饼图实例");
		// 创建图形
		pieChart.createPiePlot();
		// 生成图形
		pieChart.pack();
		// 把窗体显示到显示器中
		RefineryUtilities.centerFrameOnScreen(pieChart);
		// 设置显示图形状态
		pieChart.setVisible(true);

	}
}