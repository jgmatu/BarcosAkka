package com.masterinformatica.bingo.players;

import com.masterinformatica.bingo.actors.Manager;
import com.masterinformatica.bingo.actors.Player;
import com.masterinformatica.bingo.messages.BingoBote;
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
	
	public float[] getResultsGame() {
		float scores[] = new float[Manager.NUM_JUGADORES];
		
		for (int i = 0; i < Manager.NUM_JUGADORES; i++) {
			scores[i] = this.players[i].getScore();
		}
		return scores;
	}

	public void sendFinalize(Manager manager) {
		BingoExit exit = new BingoExit(0);
		
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			this.players[i].getActor().tell(exit, manager.getSelf());				
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
	
	public void sendBote(Manager manager, float bote[]) {
		for (int i = 0; i < Manager.NUM_JUGADORES; ++i) {
			players[i].getActor().tell(new BingoBote(bote[i]), manager.getSelf());
		}
	}
}
