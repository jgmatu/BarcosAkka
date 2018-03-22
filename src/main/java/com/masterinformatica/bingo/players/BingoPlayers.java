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
			ActorRef jugador = manager.getContext().actorOf(Props.create(Player.class));
			this.players[i] = new BingoPlayer(jugador);
		}
	}
	
	public void setScoreBingo(BingoPlayer p) {
		getBingoPlayer(p).setScoreBingo();
	}
	
	public  void setScoreLine(BingoPlayer p) {
		getBingoPlayer(p).setScoreLine();
	}
	
	private BingoPlayer getBingoPlayer(BingoPlayer player) {
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			if (player.getActor() == this.players[i].getActor()) {
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

	public void sendFinalize(Manager manager, BingoExit exit) {
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			this.players[i].getActor().tell(exit, manager.getSelf());
		}
	}	
}
