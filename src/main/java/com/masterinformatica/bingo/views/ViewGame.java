package com.masterinformatica.bingo.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ViewGame extends JPanel {

	protected int maxNumber;
	protected int numberGenerate;
	protected boolean paint;
	protected int[][] matriz;

	public ViewGame() {
		this.paint = false;
		this.numberGenerate = -1;
		this.maxNumber = -1;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (int y = 0; y < matriz.length; y++) {
			for (int x = 0; x < matriz[y].length; x++) {
				int auxX = 50 * x;
				int auxY = 50 * y;
				g2d.drawString(String.valueOf(this.matriz[y][x]), auxX + 5, auxY + 20);
			}
		}

		if (this.paint) {
			for (int y = 0; y < this.matriz.length; y++) {
				for (int x = 0; x < this.matriz[y].length; x++) {
					if (this.matriz[y][x] == this.numberGenerate) {
						int auxX = 50 * x;
						int auxY = 50 * y;
						g2d.setColor(Color.RED);
						g2d.drawRect(auxX, auxY, 50, 50);
					}
				}
			}
		}
		this.paint = false;
	}

}
