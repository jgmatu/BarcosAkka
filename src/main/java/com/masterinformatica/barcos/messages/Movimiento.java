package com.masterinformatica.barcos.messages;

public class Movimiento {

	private Coordenada coordenada;
	
	public Movimiento(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	
	@Override
	public String toString() {
		return String.format("Coord : %s", this.coordenada.toString());
	}
}
