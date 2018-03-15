package com.masterinformatica.barcos.actors;

import com.masterinformatica.bingo.entities.Bombo;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Manager extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private Bombo bombo;
	
    @Override
    public void preStart() {
    	this.bombo = new Bombo();
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

