package com.masterinformatica.barcos.logica;

import com.masterinformatica.barcos.messages.Movimiento;

public class Tablero implements Jugadas {
	
	private final int ROWS;
	private final int COLS;
	
	private boolean[][] state;
	
	
	public Tablero(int rows, int cols) {
		ROWS = rows;
		COLS = cols;
		state = new boolean[ROWS][COLS];
		initialize();
	}
	
	private void initialize() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				state[i][j] = false;
			}
		}		
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer format = new StringBuffer();

		for (boolean[] r: state) {
			for (boolean c: r) {
				format.append(String.format(" %b ", c));
			}
			format.append('\n');
		}
		return format.toString();
	}

	@Override
	public void mover(Movimiento m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ganar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hundir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existe(Movimiento m) {
		// TODO Auto-generated method stub
		return false;
	}
}
