package com.masterinformatica.bingo.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ViewBingo extends JPanel {

	protected int maxNumber;
	protected int numberGenerate;
	protected boolean paint;
	protected int[][] matriz;

	public ViewBingo() {
		this.paint = false;
		this.numberGenerate = -1;
		this.maxNumber = -1;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (!this.paint) {
			return;
		}
				
		// Print all numbers...
		for (int y = 0; y < this.matriz.length; y++) {
			for (int x = 0; x < this.matriz[y].length; x++) {
				int auxX = 50 * x;
				int auxY = 50 * y;
				g2d.drawString(String.valueOf(this.matriz[y][x]), auxX + 5, auxY + 20);
			}
		}
		
		// Draw rectangles...
		for (int y = 0; y < this.matriz.length; y++) {
			for (int x = 0; x < this.matriz[y].length; x++) {
				if (this.matriz[y][x] == this.numberGenerate) {
					int auxX = 50 * x;
					int auxY = 50 * y;
					g2d.setColor(Color.RED);
					g2d.drawRect(auxX, auxY, 25, 25);
				}
			}
		}
		this.paint = false;
	}

}
