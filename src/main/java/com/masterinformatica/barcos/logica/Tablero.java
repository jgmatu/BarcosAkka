package com.masterinformatica.barcos.logica;

public class Tablero {
	
	private boolean[][] state;
	
	public Tablero(int rows, int cols) {
		state = new boolean[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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
}
