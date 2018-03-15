package com.masterinformatica.barcos.messages;

public class Movimiento {

	private String name;
	
	public Movimiento(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
