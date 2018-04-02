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

	public static final int NUM_JUGADORES = 2;
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private BingoPlayers players;
	private BingoDiller diller;
	private boolean isBingo;
	
	public Manager() {
		this.isBingo = false;
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
		if (type == Value.LINEA) {
			players.setScoreLine(getSender());			
		}			
		getSender().tell(message, getSelf());
	}
	
	private void endGame() {
		this.isBingo = true;
		showResultsGame();				
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

	private void showResultsGame() {
		int[] scores = this.players.getResultsGame();
		int max = Integer.MIN_VALUE, idx = -1;
		
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			System.out.println(String.format("\nPlayer : %d score %d", i, scores[i]));
			if (scores[i] > max) {
				max = scores[i];
				idx = i;
			}
		}
		System.out.println(String.format("Gana Jugador : %d\n", idx));
 	}
	
	@Override
	public void unhandled(Object message) {
		this.log.error("Message not registered");
	}
}
