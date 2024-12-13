package com.my.learning.swing.piedemo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class PieDemo11 extends ApplicationFrame {
//	8.3 多饼图
//	实例 177 实现多饼图
	public PieDemo11(final String title) {
		super(title);

	}

	public static CategoryDataset createCategoryDataset(String rowKeyPrefix, String columnKeyPrefix, double[][] data) {

		DefaultCategoryDataset result = new DefaultCategoryDataset();
		for (int r = 0; r < data.length; r++) {
			String rowKey = rowKeyPrefix + (r + 1);
			for (int c = 0; c < data[r].length; c++) {
				String columnKey = columnKeyPrefix + (c + 1);
				result.addValue(new Double(data[r][c]), rowKey, columnKey);
			}
		}
		return result;

	}

	/**
	 * 创建数据集
	 *
	 * @return
	 */
	private CategoryDataset createDataset() {
		double[][] data = new double[][] { { 620, 410, 300 }, { 300, 390, 500 } };
		CategoryDataset dataset = createCategoryDataset("Dept", // 行名称
				"Month", // 列名称
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
		JFreeChart chart = ChartFactory.createMultiplePieChart("4-6 month sales ranking", // 饼图标题
				dataset, // 数据集
				TableOrder.BY_ROW, // 排序方式
				true, true, false);
		return chart;
	}

	/**
	 * 创建饼图
	 */
	public void createPiePlot() {
		JFreeChart chart = getJFreeChart();
		setContentPane(new ChartPanel(chart));
	}

	public static void main(final String[] args) {

		final PieDemo11 demo = new PieDemo11("饼图实例");
		demo.createPiePlot();
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}
}
