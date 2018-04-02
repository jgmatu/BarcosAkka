package com.masterinformatica.bingo.entities;

import java.util.Random;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.views.Painter;

public class Carton implements Painter {

	public static final int ROWS = 6;
	public static final int COLS = 2;
		
	private Casilla[] casillas;
	private boolean[] lineas;
	
	public Carton() {
		this.lineas = new boolean[ROWS];
		this.casillas = new Casilla[ROWS * COLS];
		init();
	}
	
	private void init() {		
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				this.insertNumber(i, j);
			}
			this.lineas[i] = false;
		}		
	}
	
	private void insertNumber(int row, int col) {
		Random rand = new Random();
		boolean inserted = false;

		do {
			int number = rand.nextInt(Bombo.MAX_NUMBERS);
			if (!this.contains(number)) {				
				this.casillas[row * COLS + col] = new Casilla(new Coordenada(row, col), number);															
				inserted = true;
			}
		} while (!inserted);
	}
	
	private boolean contains(int numb) {
		for (Casilla casilla : this.casillas) {
			if (casilla != null && casilla.getValue() == numb) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLinea() {	
		for (int i = 0; i < ROWS; i++) {
			if (!this.lineas[i] && this.isLineMarked(i)) {
				this.lineas[i] = true;
				return true;
			}
		}
		return false;
	}
		
	private boolean isLineMarked(int row) {
		for (int j = 0; j < COLS; ++j) {
			if (!this.casillas[row * COLS + j].isMarked()) {
				return false;
			}				
		}
		return true;
	}
	
	public boolean isBingo() {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				if (!this.casillas[i * COLS + j].isMarked()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void mark(BingoNumber numb) {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				Casilla casilla = this.casillas[i * COLS + j];
				if (casilla.isCasilla(numb.getValue())) {
					casilla.mark();					
				}
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuffer format = new StringBuffer();
		
		format.append("Carton\n");
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				format.append(String.format(" %s ", this.casillas[i * COLS + j].toString()));				
			}
			format.append('\n');
		}
		return format.toString();
	}

	
	public int[][] getPaint() {
		int[][] paint = new int[ROWS][COLS];

		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLS; ++j) {
				System.out.print(String.format(" %d ", this.casillas[i * COLS + j].getValue()));
				paint[i][j] = this.casillas[i * COLS + j].getValue();				
			}
		}
		System.out.println();
		return paint;
	}

}
