package com.masterinformatica.bingo.views;

import java.awt.Graphics;

import com.masterinformatica.bingo.entities.Carton;

@SuppressWarnings("serial")
public class ViewPlayer extends ViewGame {


	public ViewPlayer(Carton c) {
		super();
		System.out.println(c.toString());
		this.matriz = c.getPaint();
	}

	public void setNumberGenerateP(int numberGenerate, boolean paint) {
		this.numberGenerate = numberGenerate;
		this.paint = paint;
	}
	
    @Override
	public void paint(Graphics g) {
    	super.paint(g);
    }	
}
