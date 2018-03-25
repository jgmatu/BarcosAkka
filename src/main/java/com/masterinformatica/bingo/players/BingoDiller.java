package com.masterinformatica.bingo.players;

import akka.actor.ActorRef;

public class BingoDiller {

	private ActorRef diller;
	private boolean close;
	
	public BingoDiller(ActorRef actor) {
		this.diller = actor;
		this.close = false;
	}
	
	public void setClose() {
		this.close = true;
	}
	
	public boolean isClosed() {
		return this.close;
	}
	
	public ActorRef getActor() {
		return this.diller;
	}
}
