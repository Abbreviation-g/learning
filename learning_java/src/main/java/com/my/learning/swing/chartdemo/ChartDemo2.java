package com.my.learning.swing.chartdemo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ChartDemo2 {
	public JFreeChart getJFreeChart() {
        PieDataset dataset = getPieDataset();
        JFreeChart chart = ChartFactory.createPieChart("Pie title", dataset,
                true, false, false);
        return chart;
    }

    /**
     * 创建一个饼图表的数据集
     *
     * @return
     */
    private PieDataset getPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 200);
        dataset.setValue("B", 400);
        dataset.setValue("C", 500);
        return dataset;
    }

    public static void main(String[] args) {
        ChartDemo2 demo = new ChartDemo2();
        ChartFrame chartFrame = new ChartFrame("JFreeChar Demo",
                demo.getJFreeChart());
        chartFrame.pack();
        chartFrame.setVisible(true);

    }
}
