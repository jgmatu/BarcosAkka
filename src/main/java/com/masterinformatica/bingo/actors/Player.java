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
		System.out.println(this.carton.toString());    		
		
		this.id = id;
    	this.frameJ = new JFrame("Player: " + this.id);
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

    		this.markNumber(numb);
    		this.playerWindow.setNumberGenerateP(numb.getValue(), true);
    		this.playerWindow.repaint();
    	}
    	
    	if (message instanceof BingoExit) {
    		BingoExit exit = (BingoExit) message;
    		exitGame(exit);
    	}
    	 
    	if (message instanceof BingoMessage) {
    		BingoMessage validate = (BingoMessage) message;
    		viewValidate(validate);
    	}
    }

    private void markNumber(BingoNumber numb) {
		this.carton.mark(numb);

		if (this.carton.isBingo()) {
			BingoMessage msgLinea = new BingoMessage(Value.BINGO);
    		getSender().tell(msgLinea, getSelf());    			    			
    		System.err.println("Bingo!!");
		
		} else if (this.carton.isLinea()) {
			BingoMessage msgLinea = new BingoMessage(Value.LINEA);
    		getSender().tell(msgLinea, getSelf());    			
    		System.err.println("Linea!!");
		}    	
    }

    private void exitGame(BingoExit exit) {
    	getSender().tell(new BingoAck(), getSelf());

    	frameJ.dispose();
    	getContext().stop(getSelf());
    }
    
    public void viewValidate(BingoMessage validate) {
    	Value value = validate.getValue(); 
    	
    	if (value == Value.BINGO) {
    		System.out.println("He ganado!!" + this.id);
    	}
    	
    	if (value == Value.LINEA) {
    		System.out.println("Me validan la linea!!" + this.id);
    	}
    }
     
    
    @Override 
    public void unhandled(Object message) {
    	this.log.error("Message not registered");
    }
        
}