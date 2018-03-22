package com.masterinformatica.bingo.players;

import com.masterinformatica.bingo.entities.Score;

import akka.actor.ActorRef;

public class BingoPlayer {

	private ActorRef player;
	private Score score;
	
	public BingoPlayer(ActorRef player) {
		this.player = player;
		this.score = new Score();
	}
	
	public void setScoreLine() {
		this.score.setLine();
	}
	
	public void setScoreBingo() {
		this.score.setBingo();
	}
	
	public int getScore() {
		return this.score.getScore();
	}
	
	public ActorRef getActor() {
		return this.player;
	}
	
}
