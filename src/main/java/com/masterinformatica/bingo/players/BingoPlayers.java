package com.masterinformatica.bingo.players;

import com.masterinformatica.bingo.actors.Manager;
import com.masterinformatica.bingo.actors.Player;
import com.masterinformatica.bingo.messages.BingoExit;
import com.masterinformatica.bingo.messages.BingoNumber;

import akka.actor.ActorRef;
import akka.actor.Props;

public class BingoPlayers {
	
	private BingoPlayer[] players;

	public BingoPlayers(Manager manager) {
		this.players = new BingoPlayer[Manager.NUM_JUGADORES];

		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			ActorRef jugador = manager.getContext().actorOf(Props.create(Player.class, i));
			this.players[i] = new BingoPlayer(jugador, i);
		}
	}
	
	public void setScoreBingo(ActorRef actor) {
		BingoPlayer player = getBingoPlayer(actor);

		if (player != null) {
			player.setScoreBingo();			
		}
	}
	
	public  void setScoreLine(ActorRef actor) {
		BingoPlayer player = getBingoPlayer(actor);

		if (player != null) {
			player.setScoreLine();			
		}
	}
	
	private BingoPlayer getBingoPlayer(ActorRef actor) {
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			if (this.players[i].getActor() == actor) {
				return this.players[i];
			}
		}
		return null;
	}

	public void sendNumber(Manager manager, BingoNumber numb) {
		for (int i = 0; i < Manager.NUM_JUGADORES; i++) {
			ActorRef player = this.players[i].getActor();
			player.tell(numb, manager.getSelf());
		}
	}
	
	public int[] getResultsGame() {
		int scores[] = new int[Manager.NUM_JUGADORES];
		
		for (int i = 0; i < Manager.NUM_JUGADORES; i++) {
			scores[i] = this.players[i].getScore();
		}
		return scores;
	}

	public void sendFinalize(Manager manager, ActorRef winner) {
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			if (this.players[i].getActor() == winner) {
				this.players[i].getActor().tell(new BingoExit(1), manager.getSelf());				
			} else {
				this.players[i].getActor().tell(new BingoExit(0), manager.getSelf());								
			}
		}
	}	
	
	public void setPlayerClosed(ActorRef actor) {
		for (int i = 0; i < Manager.NUM_JUGADORES; i++) {
			if (this.players[i].getActor() == actor) {
				this.players[i].setClosed();
			}
		}
	}
	
	public boolean isAllPlayersClosed() {
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			if (!this.players[i].isClosed()) {
				return false;
			}
		}
		return true;
	}
}
