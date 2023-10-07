package com.springbootproject.models;

import org.springframework.stereotype.Component;

@Component
public class PlayMove {

	private MoveType type;
	private Integer coordinateX;
	private Integer coordinateY;
	private String gameId;

	public MoveType getType() {
		return type;
	}

	public void setType(MoveType type) {
		this.type = type;
	}

	public Integer getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}

	public Integer getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

}
