package com.springbootproject.services;

import com.springbootproject.exceptions.InvalidGameException;
import com.springbootproject.exceptions.InvalidMoveException;
import com.springbootproject.exceptions.InvalidParamException;
import com.springbootproject.exceptions.NoGameFoundException;
import com.springbootproject.models.Game;
import com.springbootproject.models.MoveType;
import com.springbootproject.models.PlayMove;
import com.springbootproject.models.Player;
import com.springbootproject.repositories.GameRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.springbootproject.models.GameStatus.*;

import java.util.Random;

@Service
@AllArgsConstructor
public class GameService {

	public Game beginTheGame(Player player) {

		Random rd = new Random();
		int rdInteger = (int) (Math.random() * 10);

		Game game = new Game();
		// Initialize the entire board (9 elements) with 0
		game.setBoard(new int[3][3]);
		// game.setGameId(Integer.toString(rd.nextInt()));
		game.setGameId(Integer.toString(rdInteger));
		game.setPlayer1(player);
		game.setStatus(NEW_GAME);
		game.setWhoseNextRound(MoveType.X);

		// save the game in the repository.
		// This line can be replaced with a call to
		// a jpa repository to save in a real table
		GameRepository.getInstance().setGame(game);
		return game;
	}

	public Game joinTheGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {

		// Test 1
		if (!GameRepository.getInstance().getGames().containsKey(gameId)) {
			throw new InvalidParamException("The game you are tying to join does not exist");
		}
		Game game = GameRepository.getInstance().getGames().get(gameId);

		// Test 2
		if (game.getPlayer2() != null) {
			throw new InvalidGameException("There are already 2 players in the game");
		}

		game.setPlayer2(player2);
		game.setStatus(GOING_ON_GAME);
		GameRepository.getInstance().setGame(game);
		return game;
	}

	public Game makeAmove(PlayMove playMove) throws NoGameFoundException, InvalidGameException, InvalidMoveException {

		// Test 3
		if (!GameRepository.getInstance().getGames().containsKey(playMove.getGameId())) {
			throw new NoGameFoundException("The game you are trying to play in does not exist");
		}

		Game game = GameRepository.getInstance().getGames().get(playMove.getGameId());

		// Test 4
		if (game.getStatus().equals(FINISHED_GAME)) {
			throw new InvalidGameException("The game is already finished");
		}

		// Test 5
		if (game.getPlayer2()==null) {
			throw new InvalidGameException("You can't play alone. Invite someone else to join you in this game.");
		}

		

		int[][] board = game.getBoard();

		

		// Test 6

		// Rule 1 : X always goes first.
		// Based on the PlayMove.getType()
		// and there is no move so far => all 9 positions are 0
		// make sure the first move is an X
		// ==>

		boolean thereIsAlreadyAMoveOnTheBoard = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1 || board[i][j] == 2) {
					// Somebody has already a move on the board
					thereIsAlreadyAMoveOnTheBoard = true;
					break;
				}

			}
			if (thereIsAlreadyAMoveOnTheBoard) {
				break;
			}
		}

		// If there is no previous move on the board
		// Check if current move is an X (value 1)
		if (!thereIsAlreadyAMoveOnTheBoard && playMove.getType().getValue() != 1) {
			throw new InvalidMoveException("The first move on the board has to be ABSOLUTLY an X.");
		}

		// <==

		// Test 7
		// Rule 2 : Players cannot play on a played position.
		// board[playMove.getCoordinateX()][playMove.getCoordinateY()] has to be 0

		// ==>

		boolean currentPositionAlreadyTaken = false;
		int moveInTheGivenPosition = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				int x = playMove.getCoordinateX();
				int y = playMove.getCoordinateY();
				if (i == x && j == y && board[i][j] != 0) {
					// Somebody has already a move on the specified X and Y positions
					currentPositionAlreadyTaken = true;
					moveInTheGivenPosition = board[i][j];
					break;
				}

			}
			if (currentPositionAlreadyTaken) {
				break;
			}
		}
		if (currentPositionAlreadyTaken) {
			throw new InvalidMoveException(
					"There is aleady a move in the X and Y position given " + moveInTheGivenPosition);
		}
		// <==

		// Test 8

		// Rule 3 : Players alternate placing X’s and O’s on the board
		// Make sure an X type is followed by an O type, unless we are in the first move
		// where it has to be an X
		// Make sure an O type is followed by an X type, unless we are in the first move
		// where it has to be an X
		// ==>
		if (!playMove.getType().equals(game.getWhoseNextRound())) {
			throw new InvalidMoveException("This round is for : " + game.getWhoseNextRound());
		}
		// <==

		// A given position on the board will receive
		// a 1 if it's an X move
		// a 2 if it's an O move
		// By default there is a 0
		board[playMove.getCoordinateX()][playMove.getCoordinateY()] = playMove.getType().getValue();

		// Rule 4 : If a player is able to draw three X’s or three O’s in a row, that
		// player wins.
		boolean xWinner = isThereAwinner(game.getBoard(), MoveType.X);
		boolean oWinner = isThereAwinner(game.getBoard(), MoveType.O);

		if (xWinner) {
			game.setWinner(MoveType.X);
			game.setStatus(FINISHED_GAME);
			game.setWhoseNextRound(MoveType.NOBODY);
		} else if (oWinner) {
			game.setWinner(MoveType.O);
			game.setStatus(FINISHED_GAME);
			game.setWhoseNextRound(MoveType.NOBODY);
		}

		// Rule 5: If all nine squares are filled and neither player has three in a row,
		// the game is a draw.
		// Check if the game is not in a draw situation
		// If all 9 positions on the board are filled and there is no winner
		// ==>

		boolean allNinePositionHaveAlreayBeenFilled = true;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					// there is still an empty square to be filled
					allNinePositionHaveAlreayBeenFilled = false;
					break;
				}

			}
			if (!allNinePositionHaveAlreayBeenFilled) {
				break;
			}
		}

		// No winner
		// and there is no single square remaining.
		// It's a draw
		if (!xWinner && !oWinner && allNinePositionHaveAlreayBeenFilled) {
			game.setStatus(GAME_DRAW);
			GameRepository.getInstance().setGame(game);
			return game;
			// Better send a message to the players instead of an exception
			// return game
			// throw new InvalidGameException("There is no winner. It's a draw.");
		}
		// <==

		if (playMove.getType().equals(MoveType.X)) {
			game.setWhoseNextRound(MoveType.O);
		} else {
			game.setWhoseNextRound(MoveType.X);
		}

		GameRepository.getInstance().setGame(game);
		return game;
	}

	private Boolean isThereAwinner(int[][] board, MoveType ticToe) {
		int[] gameBoard = new int[9];
		int counterIndex = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				gameBoard[counterIndex] = board[i][j];
				counterIndex++;
			}
		}

		int[][] winingSets = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
				{ 0, 4, 8 }, { 2, 4, 6 } };
		for (int i = 0; i < winingSets.length; i++) {
			int counter = 0;
			for (int j = 0; j < winingSets[i].length; j++) {
				if (gameBoard[winingSets[i][j]] == ticToe.getValue()) {
					counter++;
					if (counter == 3) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
