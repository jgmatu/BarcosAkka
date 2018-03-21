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
	
	public int getValue() {
		return this.number;
	}
	
	@Override
	public String toString() {
		return String.format("%d", this.number);
	}
}
