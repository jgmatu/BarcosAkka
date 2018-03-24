package com.masterinformatica.bingo.actors;

import javax.swing.JFrame;

import com.masterinformatica.bingo.entities.Carton;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoMessage;
import com.masterinformatica.bingo.messages.BingoMessage.Value;
import com.masterinformatica.bingo.views.ViewPlayer;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Player extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private Carton carton;
	private JFrame frameJ;
	private ViewPlayer playerWindow;

	public Player() {
    	this.carton = new Carton();
    	
    	this.frameJ = new JFrame("Player");
    	this.playerWindow = new ViewPlayer(carton);

    	this.frameJ.add(playerWindow);
        this.frameJ.setSize(200, 200);
        this.frameJ.setLocationRelativeTo(null);
        this.frameJ.setVisible(true);
        this.frameJ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    @Override
    public void onReceive(Object message)  throws Exception{    	
    	if (message instanceof BingoNumber) {
    		BingoNumber numb = (BingoNumber) message;

    		markNumber(numb);
    		
    		this.playerWindow.setNumberGenerateP(numb.getValue(), true);
    		this.playerWindow.repaint();
    		
    	} else if (message instanceof BingoExit) {
    		exitGame();
    	} else {
            unhandled(message);
    	}
    }

    private void markNumber(BingoNumber numb) {
		this.carton.mark(numb);

		if (this.carton.isBingo()) {
			BingoMessage msgLinea = new BingoMessage(Value.BINGO);
    		getSender().tell(msgLinea, getSelf());    			    			
    		System.err.println("Bingo!!");
    		System.out.println(this.carton.toString());    		
		} else if (this.carton.isLinea()) {
			BingoMessage msgLinea = new BingoMessage(Value.LINEA);
    		getSender().tell(msgLinea, getSelf());    			
    		System.err.println("Linea!!");
    		System.out.println(this.carton.toString());    		
		}    	
    }

    /**
     * Cuidado puede ser que dos jugadores 
     * tengan bimgo y solo gane el primero que
     * informe al maestro. Bug.
     */
    private void exitGame() {
    	if (this.carton.isBingo()) {
    		System.out.println("He ganado!");
    	} else {
    		System.out.println("He perdido...");
    	}
    	this.
    	getContext().stop(getSelf());
    }
    
    
    @Override 
    public void unhandled(Object message) {
    	this.log.error("Message not registered");
    }
        
}