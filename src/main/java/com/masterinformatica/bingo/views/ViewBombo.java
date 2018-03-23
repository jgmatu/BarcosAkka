package com.masterinformatica.bingo.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import com.masterinformatica.bingo.entities.Bombo;

@SuppressWarnings("serial")
public class ViewBombo extends JPanel{
	
	int[] pintados;
	int number;
    int numberGenerate;
    boolean paint = false;
    Bombo bombo = new Bombo();
    
    int filas = bombo.MAX_NUMBERS / 20;
    int columnas = bombo.MAX_NUMBERS / filas;
    int [][] matriz = new int[filas][columnas];

    public void setMaxNumber(int number){
        this.number = number;
    }
    
    public void setNumberGenerate(int numberGenerate, boolean paint){
        this.numberGenerate = numberGenerate;
        this.paint = paint;  
    }

    @Override
	public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int number = 1;
            for (int y=0; y < matriz.length; y++) {
            	  for (int x=0; x < matriz[y].length; x++) {
            		  matriz[y][x] = number;
            		  int auxX = 50 * x;
            		  int auxY = 50 * y;
                      g2d.drawString(String.valueOf(matriz[y][x]), auxX+5, auxY+20); 
            		  number ++;
            	  }
            	}

            if(paint) {
            	for (int y=0; y < matriz.length; y++) {
              	  for (int x=0; x < matriz[y].length; x++) {
              		  if(matriz[y][x] ==  numberGenerate) {
                  		  int auxX = 50 * x;
                  		  int auxY = 50 * y;
                  		  g2d.setColor(Color.RED);
      	                  g2d.drawRect(auxX, auxY, 20, 20);
              		  }
              	  }
              	}
                
            }
              
    	}   
}
