package com.my.learning.swing.piedemo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.plot.PiePlot;

public class PieDemo9Listener implements ActionListener {
	private PiePlot plot;

	// 饼图的角度
	private int angle = 90;

	public PieDemo9Listener(PiePlot plot) {
		this.plot = plot;
	}

	/*
	 * 设置饼图的角度，然后加 1，如果饼图的角度是 360 度，把角度设置为 0
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		this.plot.setStartAngle(this.angle);
		this.angle = this.angle + 1;
		if (this.angle == 360) {
			this.angle = 0;
		}
	}
}
