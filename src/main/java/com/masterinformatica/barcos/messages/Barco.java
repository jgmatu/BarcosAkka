package com.masterinformatica.barcos.messages;


public class Barco {
	
	private Coordenada[] coordenadas;
	private boolean[] state;
	

	public Barco(Coordenada[] coordenadas) {
		this.state = new boolean[coordenadas.length];
		
		for (int i = 0; i < this.state.length; i++) {
			this.state[i] = false;
		}
	}
	
	public void tocar() {
		for (int i = 0; i < this.state.length; i++) {	
			if (!this.state[i]) {
				this.state[i] = true;
				return;
			}	
		}
	}
	
	public boolean isHundido() {
		for (int i = 0; i < this.state.length; i++) {
			if (!this.state[i]) {
				return false;
			}
		}
		return true;
	}
	
}
