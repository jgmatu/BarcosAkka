package com.masterinformatica.bingo.actors;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.exceptions.ExceptionBombo;
import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoNumber;
import akka.actor.UntypedActor;

public class Diller extends UntypedActor {

	private Bombo bombo;

	public Diller() {
		this.bombo = new Bombo();		
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof BingoNumber) {
			generateNumber();
		} else if (message instanceof BingoExit) {
			exitGameWinner();
		}
	}	

	private void generateNumber() {
		try {
		
			BingoNumber numb = bombo.generate();		
			Thread.sleep(1000);
			
			System.out.println(String.format("El : %d\n", numb.getValue()));
		    getSender().tell(numb, getSelf());		    
		    
		} catch (ExceptionBombo e) {
		
			exitGameEmptyBombo();

		} catch (InterruptedException e) {
			System.err.println("Interrumped thread sleep...");		
		}
	}
	
	private void exitGameWinner() {
		System.out.println("Bombo cerrado acabó el juego...");
		getContext().stop(getSelf());
	}
	
	private void exitGameEmptyBombo() {		
		getSender().tell(new BingoExit(-1), getSender());
		System.err.println("Bombo vacío, acabar juego! no mando mas mensajes al master...");
	}

}
