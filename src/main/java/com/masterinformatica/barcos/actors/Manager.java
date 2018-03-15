package com.masterinformatica.barcos.actors;

import com.masterinformatica.barcos.messages.Coordenada;
import com.masterinformatica.barcos.messages.Movimiento;
import com.masterinformatica.barcos.messages.Barcos;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Manager extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
    	
    	// Comentar a los jugadores que manden la posición de sus barcos.
    	
        ActorRef actor2 = getContext().actorOf(Props.create(Jugador.class), "greeter");
        actor2.tell(new Movimiento(new Coordenada(0, 0)), getSelf());
    }

    @Override
    public void onReceive(Object o) {
    	if (o instanceof Barcos) {
    	
    		// Recibo mensaje de posicionamiento de barcos.
    		
    	} else if (o instanceof Movimiento) {
    		// Recibo movimiento en el tablero.
        	Movimiento msg = (Movimiento) o;
        	log.info(msg.toString());
        	waitPing();
            getSender().tell(new Movimiento(new Coordenada(0, 0)), getSelf());
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

