package com.my.learning.swing.piedemo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.plot.PiePlot;

public class PieDemo10Listener implements ActionListener {
	private PiePlot plot;

	// 饼图的角度
	private int angle = 90;

	public PieDemo10Listener(PiePlot plot) {
		this.plot = plot;
	}

	/*
	 * 设置饼图的角度，向右旋转则减 1，如果饼图的角度减到 0 时，把角度重新设置为是 360
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		this.plot.setStartAngle(this.angle);
		this.angle = this.angle - 1;
		if (this.angle == 0) {
			this.angle = 360;
		}
	}
}
