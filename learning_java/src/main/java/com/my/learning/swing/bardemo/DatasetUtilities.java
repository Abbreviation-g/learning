package com.my.learning.swing.bardemo;

import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class DatasetUtilities {
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

	public static CategoryDataset createCategoryDataset(Comparable rowKey, KeyedValues rowData) {

		DefaultCategoryDataset result = new DefaultCategoryDataset();
		for (int i = 0; i < rowData.getItemCount(); i++) {
			result.addValue(rowData.getValue(i), rowKey, rowData.getKey(i));
		}
		return result;

	}
}
