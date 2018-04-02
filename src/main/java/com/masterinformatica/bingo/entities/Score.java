package com.masterinformatica.bingo.entities;

public class Score {
	
	private static final float SCORE_LINE = 0.1f;
	private static final float SCORE_BINGO = 0.5f;
	
	private float score;
	
	public Score() {
		this.score = 0;
	}
	
	public void setLine() {
		this.score += SCORE_LINE;
	}
	
	public void setBingo() {
		this.score += SCORE_BINGO;
	}
	
	public float getScore() {
		return this.score;
	}
	
}
