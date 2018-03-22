package com.masterinformatica.bingo.entities;

import java.util.Random;
import com.masterinformatica.bingo.messages.BingoNumber;

public class Carton {

	private static final int ROWS = 3;
	private static final int COLS = 3;
	
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
				insertNumber(i, j);
			}
			this.lineas[i] = false;
		}		
	}
	
	private void insertNumber(int i, int j) {
		Random rand = new Random();
		boolean inserted = false;

		do {
			int number = rand.nextInt(Bombo.MAX_NUMBERS);
			if (!this.contains(number)) {				
				this.casillas[i * ROWS + j] = new Casilla(new Coordenada(i, j), number);															
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
<<<<<<< HEAD
			boolean[] cols = getLineMarked(i);
			if (!this.lineas[i] && isColsLine(cols)) {
=======
			if (!this.lineas[i] && this.isLineMarked(i)) {
>>>>>>> 841999c3a7aad77c1cdf30de25b57932c6623809
				this.lineas[i] = true;
				return true;
			}
		}
		return false;
	}
		
	private boolean isLineMarked(int row) {
		for (int j = 0; j < COLS; ++j) {
			if (!this.casillas[row * ROWS + j].isMarked()) {
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
}
