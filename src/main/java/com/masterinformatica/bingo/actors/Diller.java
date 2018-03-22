package com.masterinformatica.bingo.actors;

import javax.swing.JFrame;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.exceptions.ExceptionBombo;
import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoNumber;
import akka.actor.UntypedActor;
import com.masterinformatica.bingo.views.Principal;

public class Diller extends UntypedActor {

	private Bombo bombo;
	JFrame frame = new JFrame("Bingo");
	Principal graWindow = new Principal();
	
	public Diller() {
		this.bombo = new Bombo();
	
        graWindow.setMaxNumber(bombo.getMaxNumber());
        frame.add(graWindow);
        frame.setSize(1000, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
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
			
<<<<<<< HEAD
			graWindow.setNumberGenerate(numb.getValue(), true);
			graWindow.repaint();
			
		    getSender().tell(numb, getSelf());
=======
			System.out.println(String.format("El : %d\n", numb.getValue()));
		    getSender().tell(numb, getSelf());		    
>>>>>>> 841999c3a7aad77c1cdf30de25b57932c6623809
		    
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
