package com.masterinformatica.barcos.logica;

import com.masterinformatica.barcos.messages.Movimiento;

public interface Jugadas {

	public void mover(Movimiento m);
	public void perder();
	public void ganar();
	public void hundir();
	public void colocar();
	
}
