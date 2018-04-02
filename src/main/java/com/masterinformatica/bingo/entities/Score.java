package com.masterinformatica.bingo.entities;

public class Score {
	
	private static int SCORE_LINE = 10;
	private static int SCORE_BINGO = 200;
	
	private int score;
	
	public Score() {
		this.score = 0;
	}
	
	public void setLine() {
		this.score += SCORE_LINE;
	}
	
	public void setBingo() {
		this.score += SCORE_BINGO;
	}
	
	public int getScore() {
		return this.score;
	}
	
}
