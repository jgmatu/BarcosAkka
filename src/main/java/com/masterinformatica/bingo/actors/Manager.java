package com.masterinformatica.bingo.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.masterinformatica.bingo.messages.BingoAck;
import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoMessage;
import com.masterinformatica.bingo.messages.BingoMessage.Value;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.players.BingoDiller;
import com.masterinformatica.bingo.players.BingoPlayers;
import com.masterinformatica.bingo.views.ViewExit;

public class Manager extends UntypedActor {

	private static final int MAX_LINES = 5; // Este número depende del porcentaje de bote para las lineas 
											// no debe ser mayor de 10 y debe ser siempre la suma con el bigo
											// 10. Para 5 lineas el 50% del bote es por bingo.
	public static final int NUM_JUGADORES = 3;
	private static final float BOTE = 10000f; // €.
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private BingoPlayers players;
	private BingoDiller diller;
	private boolean isBingo;
	private int numLineas;
	
	public Manager() {
		this.isBingo = false;
		this.numLineas = 0;
		this.players = new BingoPlayers(this);
		this.diller = new BingoDiller(getContext().actorOf(Props.create(Diller.class)));
	}
	
	@Override
	public void preStart() {
		this.diller.getActor().tell(new BingoNumber(-1), getSelf());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof BingoMessage && !this.isBingo) {
			BingoMessage msg = (BingoMessage) message;
			proccessGameEvent(msg);
		} 

		if (message instanceof BingoNumber && !this.isBingo) {
			BingoNumber numb = (BingoNumber) message;
			setNextBall(numb);
		}
		
		if (message instanceof BingoExit && !this.isBingo) {
			BingoExit exit = (BingoExit) message;
			inesperatedExit(exit);
		}
		
		if (message instanceof BingoAck) {
			checkExit();
		}
	}
	
	public void closeSystem(BingoExit exit) {
		this.diller.getActor().tell(exit, getSelf());
		this.players.sendFinalize(this);
	}
	
	private void checkExit() {
		this.players.setPlayerClosed(getSender());

		if (diller.getActor() == getSender()) {
			diller.setClose();
		}
		
		if (diller.isClosed() && this.players.isAllPlayersClosed()) {
			getContext().stop(getSelf());			
		}
	}
	
	private void setNextBall(BingoNumber numb) {
		players.sendNumber(this, numb);
		diller.getActor().tell(new BingoNumber(-1), getSelf());		
	}
	
	private void proccessGameEvent(BingoMessage message) {		
		BingoMessage.Value type = message.getValue();

		if (type == Value.BINGO) {	
			players.setScoreBingo(getSender());
			endGame();
		}
		if (type == Value.LINEA && this.numLineas < MAX_LINES) {
			players.setScoreLine(getSender());			
			this.numLineas++;
		}			
		getSender().tell(message, getSelf());
	}
	
	private void endGame() {
		this.isBingo = true;
		sendResultsGame();				
		viewExitWindow("Cierre del juego por Bingo!");
	}

	private void inesperatedExit(BingoExit exit) {
		if (exit.getExitValue() < 0) {
			System.err.println("Bombo Vacío...");
		} 
		viewExitWindow("Cierre del juego por Bombo vacío!");
	}
		
	private void viewExitWindow(String info) {		
		ViewExit exitView = new ViewExit(info);

		exitView.setExitButton(this, getSender());
	}

	private void sendResultsGame() {
		float[] scores = this.players.getResultsGame();
		float[] bote = new float[NUM_JUGADORES];
		float max = Integer.MIN_VALUE;
		int idx = -1;
		
		for (int i = 0; i < NUM_JUGADORES; ++i) {
			if (scores[i] > max) {
				max = scores[i];
				idx = i;
			}
			bote[i] = BOTE * scores[i];
			System.out.println(String.format("Jugador : %d bote %f €", i, bote[i]));
		}
		players.sendBote(this, bote);
		System.out.println(String.format("Gana Jugador : %d", idx));
 	}
	
	@Override
	public void unhandled(Object message) {
		this.log.error("Message not registered");
	}
}
