package com.masterinformatica.barcos.actors;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.entities.Carton;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Jugador extends UntypedActor {

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
    
}


