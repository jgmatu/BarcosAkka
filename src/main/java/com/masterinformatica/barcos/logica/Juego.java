package com.masterinformatica.barcos.logica;

import com.masterinformatica.barcos.messages.Movimiento;

public class Juego implements Jugadas {
	
	private Tablero tablero;
	
	public Juego() {
		this.tablero = new Tablero(10, 10);
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
}
