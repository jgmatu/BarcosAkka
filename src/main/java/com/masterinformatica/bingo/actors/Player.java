package com.masterinformatica.bingo.actors;

import javax.swing.JFrame;

import com.masterinformatica.bingo.entities.Carton;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.messages.BingoAck;
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
	private int id;

	public Player(int id) {
    	this.carton = new Carton();
    	this.id = id;
    	this.frameJ = new JFrame("Player: " + id);
    	this.playerWindow = new ViewPlayer(carton);

    	this.frameJ.add(playerWindow);
        this.frameJ.setSize(Carton.COLS * 60, Carton.ROWS * 60);
        this.frameJ.setLocationRelativeTo(null);
        this.frameJ.setVisible(true);
        this.frameJ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frameJ.setResizable(false);
	}
	
    @Override
    public void onReceive(Object message)  throws Exception{    	
    	if (message instanceof BingoNumber) {
    		BingoNumber numb = (BingoNumber) message;

    		markNumber(numb);
    		
    		this.playerWindow.setNumberGenerateP(numb.getValue(), true);
    		this.playerWindow.repaint();
    		
    	} else if (message instanceof BingoExit) {
    		BingoExit exit = (BingoExit) message;
    		exitGame(exit);
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

    private void exitGame(BingoExit exit) {
    	System.err.println(String.format("Exit value : %d", exit.getExitValue()));
    	if (exit.getExitValue() == 0) {
    		System.err.println("He perdido...");
    	} 
    	if (exit.getExitValue() == -1) {
    		System.err.println("Bombo vacio...");    		
    	}
    	if (exit.getExitValue() == 1) {
    		System.err.println("He ganado: " + id);
    		this.playerWindow.setWin(true);
    		this.playerWindow.repaint();
    		this.frameJ.setAlwaysOnTop(true);
    	}
    	
    	getSender().tell(new BingoAck(), getSelf());
    	frameJ.dispose();
    	getContext().stop(getSelf());
    }
    
    
    @Override 
    public void unhandled(Object message) {
    	this.log.error("Message not registered");
    }
        
}