package com.my.swt.cef.win64.view;

import java.util.Arrays;
import java.util.List;

public class NewGridData {
//	var newGridData = {
//			name:'新变量300',
//			data:[['用例3',300],['用例4',200],['用例5',100]],
//			isInput:false,
//			gridIndex:0
//		};
	private String name;
	private String[][] data;
	private boolean isInput;
	// 0|1
	private int gridIndex;
	public NewGridData(String name, String[][] data, boolean isInput, int gridIndex) {
		super();
		this.name = name;
		this.data = data;
		this.isInput = isInput;
		this.gridIndex = gridIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
	public boolean isInput() {
		return isInput;
	}
	public void setInput(boolean isInput) {
		this.isInput = isInput;
	}
	public int getGridIndex() {
		return gridIndex;
	}
	public void setGridIndex(int gridIndex) {
		this.gridIndex = gridIndex;
	}
	
	public static List<NewGridData> sampleData(){
		NewGridData gridData1 = new NewGridData("变量1", new String[][]{{"用例1","1"},{"用例2","2"},{"用例3","3"}}, true, 0);
		NewGridData gridData2 = new NewGridData("变量2", new String[][]{{"用例4","4"},{"用例5","5"},{"用例6","6"}}, false, 0);
		NewGridData gridData3 = new NewGridData("变量3", new String[][]{{"用例1","100"},{"用例2","200"},{"用例3","300"}}, true, 1);
		NewGridData gridData4 = new NewGridData("变量4", new String[][]{{"用例6","600"},{"用例5","500"},{"用例4","400"}}, false, 1);
		
		return Arrays.asList(gridData1,gridData2,gridData3,gridData4);
	}
}

