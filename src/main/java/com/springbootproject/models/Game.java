package com.springbootproject.models;

import java.util.Arrays;

import org.springframework.stereotype.Component;


@Component
public class Game {

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public MoveType getWinner() {
		return winner;
	}

	public void setWinner(MoveType winner) {
		this.winner = winner;
	}

	private String gameId;
	private Player player1;
	private Player player2;
	private GameStatus status;
	private int[][] board;
	private MoveType winner;
	private MoveType whoseNextRound;
	public MoveType getWhoseNextRound() {
		return whoseNextRound;
	}

	public void setWhoseNextRound(MoveType whoseNextRound) {
		this.whoseNextRound = whoseNextRound;
	}
	

}
