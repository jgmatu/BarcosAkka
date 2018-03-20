package com.masterinformatica.barcos.actors;

import java.util.ArrayList;
import java.util.List;

import com.masterinformatica.bingo.entities.Bombo;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.masterinformatica.bingo.messages.Number;

public class Manager extends UntypedActor {
	
	//Mensajes
	public static enum msj {JUGADOR_LISTO};
	//Lista de Jugadores
	private List<ActorRef> Jugadores;
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private Bombo bombo;
	
    @Override
    public void preStart() {
    	this.bombo = new Bombo();

    	//Instancia de jugadores
    	Jugadores = new ArrayList<>();
    	System.err.println(bombo.toString());
    	
    }

    @Override
    public void onReceive(Object message)  throws Exception{
    	
    	log.info("[Manager '{}'] ha recibido el mensaje: \"{}\".", getSelf().toString(), message);
    	//El Manager recibirá in LISTO de todos los jugadores y los agrega a la lista
        if (message == Manager.msj.JUGADOR_LISTO) {
        	Jugadores.add(getSender());

            if(!Jugadores.isEmpty()){
            	Thread.sleep(3500);
            	System.err.println("Hay jugadores para empezar");
            	//Si hay mas de un jugador empiece a cantar los números
            }
            	Number numb = this.bombo.generate();
            	for (ActorRef jugador : Jugadores) {
            		jugador.tell(numb, getSelf());
            	}
            }else{
                unhandled(message);
            }
    }
        
    @Override 
    public void unhandled(Object message) {
    	log.error("Message not registered");
    }
}


























/*public class Manager extends UntypedActor {

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
}*/

