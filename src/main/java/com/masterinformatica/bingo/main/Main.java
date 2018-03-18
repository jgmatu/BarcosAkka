package com.masterinformatica.bingo.main;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.entities.Carton;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static ActorSystem actorSystem;
	public static ActorRef Jugador1;
	public static ActorRef Jugador2;
	public static ActorRef Jugador3;
	public static ActorRef Manager1;
	
	
	
	
	static public class Jugador extends UntypedActor {
		
		//Mensajes
		public enum msj {LISTO, LINEA, BINGO};
		

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
	    		Manager1.tell(Manager.msj.JUGADOR_LISTO, getSelf());
	    		System.err.println(carton.toString());
	        }else{
	            unhandled(message);
	        }
	    	
	    }
	    
	    @Override 
	    public void unhandled(Object message) {
	    	log.error("Message not registered");
	    }
	    
	}
	
	
	
	static public class Manager extends UntypedActor {
		
		//Mensajes
		public enum msj {JUGADOR_LISTO};
		
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

	            }else{
	                unhandled(message);
	            }
	    }
	        
	    @Override 
	    public void unhandled(Object message) {
	    	log.error("Message not registered");
	    }
	}

	
    public static void main(String[] args) throws InterruptedException{  
    	
    	/*Bombo bombo = new Bombo();
    	Carton carton = new Carton();
    	
    	System.err.println(bombo.toString());
    	System.err.println(carton.toString());*/

    	actorSystem = ActorSystem.create("ActorSystem");
    	Manager1 = actorSystem.actorOf(Props.create(Manager.class), "Manager");
        Jugador1 = actorSystem.actorOf(Props.create(Jugador.class), "Jugador1");
        Jugador2 = actorSystem.actorOf(Props.create(Jugador.class), "Jugador2");
        Jugador3 = actorSystem.actorOf(Props.create(Jugador.class), "Jugador3");

        Jugador1.tell(Jugador.msj.LISTO, ActorRef.noSender());
        Jugador2.tell(Jugador.msj.LISTO, ActorRef.noSender());
        Jugador3.tell(Jugador.msj.LISTO, ActorRef.noSender());
        
        akka.Main.main(new String[]{});
    }
    
}
