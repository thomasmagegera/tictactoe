package com.springbootproject.controllers;

import com.springbootproject.contollers.requests.JoinRequest;
import com.springbootproject.exceptions.InvalidGameException;
import com.springbootproject.exceptions.InvalidMoveException;
import com.springbootproject.exceptions.InvalidParamException;
import com.springbootproject.exceptions.NoGameFoundException;
import com.springbootproject.models.Game;
import com.springbootproject.models.PlayMove;
import com.springbootproject.models.Player;
import com.springbootproject.services.GameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.springbootproject.models.GameStatus.GAME_DRAW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(GameController.BASE_URL)
public class GameController {

	public static final String BASE_URL = "/game/tictactoe";

	@Autowired
	private final GameService gameService = null;

	private static final Logger log = LoggerFactory.getLogger(GameController.class);

	@PostMapping("/begin")
	public ResponseEntity<Game> beginTheGame(@RequestBody Player player) {
		log.info("The game begins here. First participant.  Request: {}", player);
		return ResponseEntity.ok(gameService.beginTheGame(player));
	}

	@PostMapping("/join")
	public ResponseEntity<Game> joinTheGame(@RequestBody JoinRequest request)
			throws InvalidParamException, InvalidGameException {
		log.info("The second participant joins the game.  Request: {}", request);
		return ResponseEntity.ok(gameService.joinTheGame(request.getPlayer(), request.getGameId()));
	}

	@PostMapping("/makeamove")
	public ResponseEntity<Game> makeAmove(@RequestBody PlayMove request)
			throws NoGameFoundException, InvalidGameException, InvalidMoveException {
		log.info("Call makeamove: {}", request);

	
		Game game = gameService.makeAmove(request);

		// Test draw
		if (game.getStatus().equals(GAME_DRAW)) {
			log.info("The game is a draw");
		}

		return ResponseEntity.ok(game);
	}

}
