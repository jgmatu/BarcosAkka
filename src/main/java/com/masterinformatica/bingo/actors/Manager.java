package com.masterinformatica.bingo.actors;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.masterinformatica.bingo.messages.BingoMessage;
import com.masterinformatica.bingo.messages.Number;

public class Manager extends UntypedActor {

	private static final int NUM_JUGADORES = 1;
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private List<ActorRef> jugadores;
	private ActorRef diller;
	
	@Override
	public void preStart() {
		this.jugadores = new ArrayList<>();

		for (int i = 0; i < NUM_JUGADORES; ++i) {
			ActorRef jugador = getContext().actorOf(Props.create(Jugador.class));
			this.jugadores.add(jugador);
		}	

		this.diller = getContext().actorOf(Props.create(Diller.class));
		this.diller.tell(new Number(-1), getSelf());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof BingoMessage) {
			BingoMessage msg = (BingoMessage) message;
			processGame(msg);
		}
		
		if (message instanceof Number) {
			Number numb = (Number) message;
			sendNumber(numb);
		}
	}
	
	private void processGame(BingoMessage message) {		
		BingoMessage.Value type = message.getValue();
		
		switch (type) {
		case BINGO:
			System.err.println("BINGO!!");
			break;		
		case LINEA:
			System.err.println("LINEA!!");			
			break;
			
		case JUGADOR_LISTO:
			System.err.println("LISTO!!");			
			break;
			
		default:
			// error...
		}
	}
	
	private void sendNumber(Number numb) {
		for (ActorRef jugador : this.jugadores) {
			jugador.tell(numb, getSelf());
		}		
		diller.tell(new Number(0), getSelf());
	}

	@Override
	public void unhandled(Object message) {
		log.error("Message not registered");
	}
}

/*
 * public class Manager extends UntypedActor {
 * 
 * private final LoggingAdapter log = Logging.getLogger(getContext().system(),
 * this);
 * 
 * private Bombo bombo;
 * 
 * @Override public void preStart() { this.bombo = new Bombo(); }
 * 
 * @Override public void onReceive(Object o) { ; }
 * 
 * @Override public void unhandled(Object o) {
 * log.error("Message not registered"); } }
 */
