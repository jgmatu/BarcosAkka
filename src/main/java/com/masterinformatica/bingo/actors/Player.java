package com.masterinformatica.bingo.actors;

import com.masterinformatica.bingo.entities.Carton;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.messages.BingoMessage;
import com.masterinformatica.bingo.messages.BingoMessage.Value;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Player extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private Carton carton;

	public Player() {
    	this.carton = new Carton();
	}
	
    @Override
    public void onReceive(Object message)  throws Exception{    	
    	if (message instanceof BingoNumber) {
    		BingoNumber numb = (BingoNumber) message;
    	
    		this.carton.mark(numb);
    		
    		if (this.carton.isLinea()) {
    			BingoMessage msgLinea = new BingoMessage(Value.LINEA);
        		getSender().tell(msgLinea, getSelf());    			
    		}
    		
    		if (this.carton.isBingo()) {
    			BingoMessage msgLinea = new BingoMessage(Value.BINGO);
        		getSender().tell(msgLinea, getSelf());    			    			
    		}
    		System.out.println(String.format("Number received!!! : %d", numb.getValue()));    		
    		System.out.println(this.carton.toString());
    	} else {
            unhandled(message);
    	}
    }
    
    @Override 
    public void unhandled(Object message) {
    	log.error("Message not registered");
    }
        
}





























/*public class Jugador extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private Carton carton;
	
    @Override
    public void preStart() {
    	this.carton = new Carton();
    }

    @Override
    public void onReceive(Object o) {
    	;
    }
    
    @Override 
    public void unhandled(Object o) {
    	log.error("Message not registered");
    }
    
}*/


