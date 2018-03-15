package com.masterinformatica.bingo.messages;

/**
 * Esta clase envia el numero del bombo a los jugadores.
 * 
 * @author javi, jonathan
 *
 */
public class Number {
	
	private int number;
	
	public Number(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
}
