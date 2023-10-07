package com.springbootproject.repositories;

import java.util.HashMap;
import java.util.Map;

import com.springbootproject.models.Game;

public class GameRepository {

    private static Map<String, Game> games;
    private static GameRepository instance;

    //Make sure it's a singleton to avoid
    //creating more than one instance of this class
    private GameRepository() {
    	//initialise the map that will store the games
        games = new HashMap<>();
    }

    public static synchronized GameRepository getInstance() {
        if (instance == null) {
            instance = new GameRepository();
        }
        return instance;
    }

    //We are simulating the retrieving of a game from a database table
    //in case we use jpa
    public Map<String, Game> getGames() {
        return games;
    }

    // Here we are simulating the saving in the database table
    //in case we use jpa
    public void setGame(Game game) {
        games.put(game.getGameId(), game);
    }
}
