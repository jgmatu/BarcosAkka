package com.masterinformatica.barcos.actors;

import com.masterinformatica.barcos.messages.Movimiento;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Jugador extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o) {
    	
        if (o instanceof Movimiento) {
        	Movimiento msg = (Movimiento) o;

        	log.info(msg.toString());
        	waitPing();
            getSender().tell(new Movimiento("Ping!"), getSelf());

        } else {
            unhandled(o);
        }
    }
    
    private void waitPing() {
    	try {
    		Thread.sleep(1500);
    	} catch (InterruptedException e) {
    		;
    	}
    }
    
    @Override 
    public void unhandled(Object o) {
    	log.error("Message not registered");
    }
    
}


