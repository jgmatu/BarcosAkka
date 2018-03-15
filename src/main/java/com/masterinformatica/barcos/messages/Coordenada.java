package com.masterinformatica.barcos.messages;

public class Coordenada {

	private int x;
	private int y;
	
	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return String.format("x: %d, y : %d", this.x, this.y);
	}
	
}
