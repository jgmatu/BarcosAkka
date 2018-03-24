package com.masterinformatica.bingo.actors;

import javax.swing.JFrame;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.exceptions.ExceptionBombo;
import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoNumber;
import akka.actor.UntypedActor;
import com.masterinformatica.bingo.views.ViewBombo;

public class Diller extends UntypedActor {

	private Bombo bombo;
	private JFrame frame;
	private ViewBombo dillerWindow;
	
	public Diller() {
		bombo = new Bombo();

		dillerWindow = new ViewBombo(bombo);
        dillerWindow.setMaxNumber(Bombo.MAX_NUMBERS);
        
        frame = new JFrame("Bingo");
        frame.add(dillerWindow);
        frame.setSize(1000, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof BingoNumber) {
			generateNumber();
		} 
		
		if (message instanceof BingoExit) {
			exitGame();
		}
	}	

	private void generateNumber() {
		try {
		
			BingoNumber numb = this.bombo.generate();					
			
			this.dillerWindow.setNumberGenerate(numb.getValue(), true);
			this.dillerWindow.repaint();
			
			Thread.sleep(500);			
			System.out.println(String.format("El: %d", numb.getValue()));
			Thread.sleep(500);
			getSender().tell(numb, getSelf());
		    
		} catch (ExceptionBombo e) {
		
			sendExitEmptyBombo();

		} catch (InterruptedException e) {

			System.err.println("Interrumped thread sleep...");		
		
		}
	}
	
	private void exitGame() {
		System.out.println("Bombo cerrado acabó el juego...");
		getContext().stop(getSelf());
	}
	
	private void sendExitEmptyBombo() {		
		getSender().tell(new BingoExit(-1), getSender());
		System.err.println("Bombo vacío, acabar juego! no mando mas mensajes al master...");
	}

}
