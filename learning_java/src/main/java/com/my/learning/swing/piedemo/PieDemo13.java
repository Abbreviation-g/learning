package com.my.learning.swing.piedemo;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class PieDemo13 extends ApplicationFrame {
//	实例 179 多饼图的展示方式
	public PieDemo13(final String title) {
		super(title);

	}

	/**
	 * 创建数据集
	 *
	 * @return
	 */
	private CategoryDataset createDataset() {
		double[][] data = new double[][] { { 620, 410, 300 }, { 300, 390, 500 } };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset("部门", // 行名称
				"月份", // 列名称
				data);
		return dataset;
	}

	/**
	 * 获取数据集，生成 JFreeChart，
	 *
	 * @return
	 */
	private JFreeChart getJFreeChart() {
		CategoryDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createMultiplePieChart("4-6 月销售排行", // 饼图标题
				dataset, // 数据集
				TableOrder.BY_COLUMN, // 排序方式
				true, true, false);
		return chart;
	}

	/**
	 * 创建饼图
	 */
	public void createPiePlot() {
		JFreeChart chart = getJFreeChart();
		// 窗体标题
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

		// 图例
		LegendTitle legendTitle = chart.getLegend();
		legendTitle.setItemFont(new Font("宋体", Font.PLAIN, 14));

		MultiplePiePlot multiplePiePlot = (MultiplePiePlot) chart.getPlot();
		JFreeChart jFreeChart = multiplePiePlot.getPieChart();

		// 图表标签
		PiePlot piePlot = (PiePlot) jFreeChart.getPlot();
		piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
		// 图表标题
		TextTitle textTitle2 = jFreeChart.getTitle();
		textTitle2.setFont(new Font("宋体", Font.BOLD, 20));

		setContentPane(new ChartPanel(chart));
	}

	public static void main(final String[] args) {

		final PieDemo13 demo = new PieDemo13("饼图实例");
		demo.createPiePlot();
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}
}
