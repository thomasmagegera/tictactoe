package com.springbootproject.contollers.requests;

import org.springframework.stereotype.Component;

import com.springbootproject.models.Player;

import lombok.Data;


@Component
public class JoinRequest {
    private Player player;
    private String gameId;
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
