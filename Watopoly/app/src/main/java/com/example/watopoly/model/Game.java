package com.example.watopoly.model;

import java.util.ArrayList;

public class Game {
    private static Game gameState = new Game();

    private ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private int turnNumber = -1;

    //Singleton
    private Game(){}

    public static Game getInstance() {
        return gameState;
    }

    //private methods
    private void setupBoard() {
        //TODO: populate tiles
    }

    //public methods
    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player nextTurn() {
        turnNumber ++;
        return players.get(turnNumber % players.size());
    }

    public Player getCurrentPlayer() {
        return players.get(turnNumber % players.size());
    }
}
