package com.masterinformatica.bingo.actors;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.entities.ExceptionBombo;
import com.masterinformatica.bingo.messages.Number;
import akka.actor.UntypedActor;

public class Diller extends UntypedActor {

	private Bombo bombo;
	
	@Override
	public void preStart() {
		System.out.println("Bombo running!");
		this.bombo = new Bombo();
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Number) {
			generateNumber();
		}
	}
	
	private void generateNumber() {
		try {
			System.out.println("Bombo generate number!");
			Number numb = bombo.generate();		

			Thread.sleep(1000);
		    getSender().tell(numb, getSelf());
		    System.out.println("Number send!");
		} catch (ExceptionBombo e) {
			System.err.println("Bombo vacío, acabar juego!");
		} catch (InterruptedException e) {
			System.err.println("Interrumped thread sleep...");		
		}

	}
}
