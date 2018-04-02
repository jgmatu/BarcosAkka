package com.masterinformatica.bingo.views;

import java.awt.Color;
import java.awt.Graphics;

import com.masterinformatica.bingo.entities.Carton;

@SuppressWarnings("serial")
public class ViewPlayer extends ViewBingo {

	private boolean winner;
	private float bote;
	
	public ViewPlayer(Carton c) {
		super();
		this.bote = -1.0f;
		this.winner = false;
		this.matriz = c.getPaint();
	}

	public void setNumberGenerateP(int numberGenerate, boolean paint) {
		this.numberGenerate = numberGenerate;
		this.paint = paint;
	}
	
	public void setWinner() {
		this.winner = true;
	}
	
	public void setBote(float bote) {
		this.bote = bote;
	}
	
    @Override
	public void paint(Graphics g) {
    	super.paint(g);
    	    	
		if(this.winner) {
			g.setColor(Color.RED);
			g.drawString("Winner", this.getWidth() / 2,  this.getHeight() - 40);
		}
		
		if (this.bote != -1.0f) {
			g.setColor(Color.GREEN);
			g.drawString("Bote : " + this.bote + " €", (this.getWidth() - 50) / 2 ,  this.getHeight() - 20);					
		}
    }
}
