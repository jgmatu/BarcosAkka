package com.masterinformatica.bingo.entities;

public class Casilla {

	private Coordenada coordenada;
	private int number;
	private boolean state;
	
	
	public Casilla(Coordenada coordenada, int number) {
		this.coordenada = coordenada;
		this.number = number;
		this.state = false;
	}
	
	public boolean isMarked() {
		return this.state;
	}
	
	
	@Override
	public String toString() {
		return String.format(" %s %d %b ", this.coordenada.toString(), this.number, this.state);
	}
	
}
