package com.masterinformatica.bingo.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoMessage;
import com.masterinformatica.bingo.messages.BingoMessage.Value;
import com.masterinformatica.bingo.messages.BingoNumber;
import com.masterinformatica.bingo.players.BingoPlayer;
import com.masterinformatica.bingo.players.BingoPlayers;

public class Manager extends UntypedActor {

	public static final int NUM_JUGADORES = 2;
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	
	private BingoPlayers players;
	private ActorRef diller;

	public Manager() {
		this.players = new BingoPlayers(this);
		this.diller = getContext().actorOf(Props.create(Diller.class));
	}
	
	@Override
	public void preStart() {
		this.diller.tell(new BingoNumber(-1), getSelf());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof BingoMessage) {
			BingoMessage msg = (BingoMessage) message;
			proccessGameEvent(msg);
		} 
		
		if (message instanceof BingoNumber) {
			BingoNumber numb = (BingoNumber) message;
			setNextBall(numb);
		}
		
		if (message instanceof BingoExit) {
			BingoExit exit = (BingoExit) message;
			inesperatedExit(exit);
		}
	}
	
	private void setNextBall(BingoNumber numb) {
		players.sendNumber(this, numb);
		diller.tell(new BingoNumber(-1), getSelf());		
	}
	
	private void proccessGameEvent(BingoMessage message) {		
		BingoMessage.Value type = message.getValue();
		
		if (type == Value.BINGO) {
			showResultsGame();			
			closeSystem(new BingoExit(0));
		} 
		
		if (type == Value.LINEA) {
			players.setScoreBingo(new BingoPlayer(getSender()));			
		}
	}

	private void inesperatedExit(BingoExit exit) {
		if (exit.getExitValue() < 0) {
			System.err.println("Bombo Vacío...");
		} 
		closeSystem(new BingoExit(-1));
	}
	
	private void showResultsGame() {
		int[] scores = this.players.getResultsGame();
		int max = Integer.MIN_VALUE, idx = -1;
		
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			System.out.println(String.format("\nPlayer : %d score %d\n", i, scores[i]));
			if (scores[i] > max) {
				max = scores[i];
				idx = i;
			}
		}
		System.out.println(String.format("Gana Jugador : %d\n", idx));
 	}
	
	private void closeSystem(BingoExit exit) {
		this.players.sendFinalize(this, exit);
		this.diller.tell(exit, getSelf());
		getContext().stop(getSelf());
	}

	@Override
	public void unhandled(Object message) {
		this.log.error("Message not registered");
	}
}
