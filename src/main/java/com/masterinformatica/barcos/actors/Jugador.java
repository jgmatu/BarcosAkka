package com.masterinformatica.barcos.actors;

import com.masterinformatica.bingo.entities.Carton;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Jugador extends UntypedActor {
	
	//Mensajes
	public static enum msj {LISTO, LINEA, BINGO};
	ActorRef manager;

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private Carton carton;
	
    @Override
    public void preStart() {
    	this.carton = new Carton();
    	
    }

    @Override
    public void onReceive(Object message)  throws Exception{
    	log.info("[Jugador '{}'] ha enviado el mensaje: \"{}\".", getSelf().toString(), message);
    	
    	if(message == msj.LISTO){
    		manager.tell(Manager.msj.JUGADOR_LISTO, getSelf());
    		//Manager1.tell(Manager.msj.JUGADOR_LISTO, getSelf());
    		System.err.println(carton.toString());
        }else{
            unhandled(message);
        }
    }
    
    @Override 
    public void unhandled(Object message) {
    	log.error("Message not registered");
    }
    
    
    public void setManager(ActorRef manager) {
    	this.manager = manager;
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


