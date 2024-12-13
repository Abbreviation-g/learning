/**
 * 
 */
package com.my.learning.swing.bardemo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RefineryUtilities;

/**
 * @author Enjing.Guo
 *
 */
public class BarDemo1 extends ApplicationFrame {
//	8.4 基本柱形图
//	实例 181 简单柱形图
	public BarDemo1(String title) {
		super(title);
	}

	/**
	 * 创建一个数据集
	 *
	 * @return
	 */
	private CategoryDataset getCategoryDataset() {

		DefaultKeyedValues keyedValues = new DefaultKeyedValues();
		keyedValues.addValue("1", 310);
		keyedValues.addValue("2", 489);
		keyedValues.addValue("3", 512);
		keyedValues.addValue("4", 589);
		keyedValues.addValue("5", 359);
		keyedValues.addValue("6", 402);
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset("java book", keyedValues);
		return dataset;
	}

	/**
	 * 生成 JFreeChart
	 *
	 * @return
	 */
	private JFreeChart getJFreeChart() {
		CategoryDataset dataset = getCategoryDataset();
		JFreeChart chart = ChartFactory.createBarChart("2010.1-6 sales volume", // 图表标题
				"month", // x 轴标签
				"sales", // y 轴标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				false, // 是否显示图例 (对于简单的柱状图必须是 false)
				false, // 是否生成工具
				false // 是否生成 URL 链接
		);

		return chart;
	}

	/**
	 * 设置图表
	 *
	 * @param chart
	 */
	public void createPlot() {
		JFreeChart chart = getJFreeChart();
		// 把 JFreeChart 面板保存在窗体里
		setContentPane(new ChartPanel(chart));

	}

	public static void main(String[] args) {
		BarDemo1 barDemo = new BarDemo1("柱形图实例");
		barDemo.createPlot();
		barDemo.pack();
		// 把窗体显示到显示器中央
		RefineryUtilities.centerFrameOnScreen(barDemo);
		// 设置可以显示
		barDemo.setVisible(true);

	}

}
