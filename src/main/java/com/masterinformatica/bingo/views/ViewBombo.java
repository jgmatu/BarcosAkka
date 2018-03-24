package com.masterinformatica.bingo.views;

import java.awt.Graphics;

import com.masterinformatica.bingo.entities.Bombo;

@SuppressWarnings("serial")
public class ViewBombo extends ViewGame {
		
    protected final int ROWS = Bombo.MAX_NUMBERS / 20;
    protected final int COLS = Bombo.MAX_NUMBERS / ROWS;

    public ViewBombo(Bombo b) {
    	super();
    	this.matriz = b.getPaint();
    }
    
    public void setMaxNumber(int number){
        this.maxNumber = number;
    }
    
    public void setNumberGenerate(int numberGenerate, boolean paint){
        this.numberGenerate = numberGenerate;
        this.paint = paint;  
    }
    
    @Override
	public void paint(Graphics g) {
    	super.paint(g);
    }
}
