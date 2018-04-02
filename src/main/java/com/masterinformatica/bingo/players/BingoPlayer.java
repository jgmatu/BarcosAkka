package com.masterinformatica.bingo.players;

import com.masterinformatica.bingo.entities.Score;

import akka.actor.ActorRef;

public class BingoPlayer {

	private ActorRef player;
	private Score score;
	private boolean close;
	private int id;
	
	public BingoPlayer(ActorRef player, int id) {
		this.player = player;
		this.score = new Score();
		this.close = false;
		this.id = id;
	}
	
	public void setScoreLine() {
		this.score.setLine();
	}
	
	public void setScoreBingo() {
		this.score.setBingo();
	}
	
	public float getScore() {
		return this.score.getScore();
	}
	
	public ActorRef getActor() {
		return this.player;
	}
	
	public void setClosed() {
		this.close = true;
	}
	
	public boolean isClosed() {
		return this.close;
	}
	
	public int getId() {
		return this.id;
	}
}
