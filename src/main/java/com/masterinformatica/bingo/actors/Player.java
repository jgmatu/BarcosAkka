package com.masterinformatica.bingo.actors;

import javax.swing.JFrame;

import com.masterinformatica.bingo.entities.Carton;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.messages.BingoAck;
import com.masterinformatica.bingo.messages.BingoBote;
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
    	this.playerWindow = new ViewPlayer(this.carton);

    	this.frameJ.add(this.playerWindow);
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
    	}
    	
    	if (message instanceof BingoExit) {
    		BingoExit exit = (BingoExit) message;
    		exitGame(exit);
    	}
    	 
    	if (message instanceof BingoMessage) {
    		BingoMessage validate = (BingoMessage) message;
    		viewValidate(validate);
    	}
    	
    	if (message instanceof BingoBote) {
    		BingoBote bote = (BingoBote) message;
    		this.playerWindow.setBote(bote.getBote());
    	}
		this.playerWindow.repaint();
    }

    private void markNumber(BingoNumber numb) {
		this.carton.mark(numb);

		if (this.carton.isBingo()) {
			BingoMessage msgLinea = new BingoMessage(Value.BINGO);
    		getSender().tell(msgLinea, getSelf());    			    			
    		System.out.println("Bingo!!");
		
		} else if (this.carton.isLinea()) {
			BingoMessage msgLinea = new BingoMessage(Value.LINEA);
    		getSender().tell(msgLinea, getSelf());    			
    		System.out.println("Linea!!");
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
    		this.playerWindow.setWinner();
    		System.out.println(getJugadorPrefix() + " he ganado!!");
    	}
    	
    	if (value == Value.LINEA) {
    		System.out.println(getJugadorPrefix() + " he hecho linea!");
    	}
    }
     
    private String getJugadorPrefix() {
    	return "Jugador " + this.id + ":";
    }
    
    @Override 
    public void unhandled(Object message) {
    	this.log.error("Message not registered");
    }
        
}