package com.masterinformatica.barcos.messages;

public class Barcos {

	private Barco[] barcos;

	public Barcos(Barco[] barcos) {
		this.barcos = barcos;
	}
	
	public Barcos(int num) {
		this.barcos = new Barco[num];
		random();
	}
	
	private void random() {
		
	}
	
	@Override
	public String toString() {
		return this.barcos.toString();
	}
}
