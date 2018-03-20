package com.masterinformatica.barcos.actors;

import java.util.ArrayList;
import java.util.List;

import com.masterinformatica.bingo.entities.Bombo;
import com.masterinformatica.bingo.entities.ExceptionBombo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.masterinformatica.bingo.messages.BingoMessage;
import com.masterinformatica.bingo.messages.Number;

public class Manager extends UntypedActor {

	private static final int NUM_JUGADORES = 10;

	private List<ActorRef> jugadores;

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private Bombo bombo;

	@Override
	public void preStart() {
		this.bombo = new Bombo();
		this.jugadores = new ArrayList<>();

		for (int i = 0; i < NUM_JUGADORES; ++i) {
			ActorRef jugador = getContext().actorOf(Props.create(Jugador.class), "NameJugador");
			this.jugadores.add(jugador);
		}		

		
		
		/**
		 * TODO: Podemos implementar un thread que haga el trabajo
		 * el master debe saber hablar con el thread por un objeto 
		 * compartido con su hilo... El bombo..
		 */
		new Thread() {
			@Override
			public void run() {
				/**
				 * TODO:
				 *  ¿Donde metemos esto? :/
				 *  Hay que pararlo si se grita bingo...
				 *  debe generar números aleatorios cada x tiempo.
				 */
				try {
					for (;;) {
					    Number numb = bombo.generate();				
				    	for (ActorRef jugador : jugadores) {
				    		jugador.tell(numb, getSelf());
				    		Thread.sleep(1000);
				    	}			
					}
				} catch (ExceptionBombo e) {
					System.err.println("Bombo vacío, acabar juego!");
				} catch (InterruptedException e) {
					System.err.println("Interrumped thread sleep...");
				}
			}
		}.start();
	}

	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof BingoMessage) {
			BingoMessage msg = (BingoMessage) message;

			BingoMessage.Value type = msg.getValue();
			switch (type) {
			case BINGO:
				break;
				
			case LINEA:
				break;
				
			case JUGADOR_LISTO:
				break;
			default:
				// error...
			}
		}
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
