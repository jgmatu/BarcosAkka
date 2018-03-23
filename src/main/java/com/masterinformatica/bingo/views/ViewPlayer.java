package com.masterinformatica.bingo.views;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import com.masterinformatica.bingo.entities.Carton;

@SuppressWarnings("serial")
public class ViewPlayer extends JPanel{
	
	Carton carton = new Carton();
	int[][] matriz;
	boolean paintnumber = false;
	int numberGenerate;
	
	public ViewPlayer (Carton c) {
		carton = c;
	}
	
    public void setNumberGenerateP(int numberGenerate, boolean paint){
        this.numberGenerate = numberGenerate;
        this.paintnumber = paint;  
    }
	
	@Override
	public void paint(Graphics gp) {
	        Graphics2D g2dp = (Graphics2D) gp;
	        
	        matriz = carton.getPaint();
	        
	         for (int y=0; y < matriz.length; y++) {
	        	  for (int x=0; x < matriz[y].length; x++) {
	        		  int auxX = 50 * x;
	        		  int auxY = 50 * y;
	                  g2dp.drawString(String.valueOf(matriz[y][x]), auxX+5, auxY+20); 
	        	  }
	        	}
	        
	        /*if(paintnumber) {
	        	for (int y=0; y < matriz.length; y++) {
	          	  for (int x=0; x < matriz[y].length; x++) {
	          		  if(matriz[y][x] ==  numberGenerate) {
	              		  int auxX = 50 * x;
	              		  int auxY = 50 * y;
	              		  g2dp.setColor(Color.RED);
	  	                  g2dp.drawRect(auxX, auxY, 20, 20);
	          		  }
	          	  }
	          	}
	            
	        } */
	        
	        
	        
		}
	
	
	

}
