package com.example.watopoly.model;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private static Game gameState = new Game();

    private ArrayList<Player> players = new ArrayList<>();
    private Board board = new Board();
    private int turnNumber = -1;

    //Singleton
    private Game(){}

    public static Game getInstance() {
        return gameState;
    }

    //public methods
    public void setTiles(ArrayList<Tile> tiles) {
        board.setTiles(tiles);
    }

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

    public void resetGame() {
        players.clear();
        //TODO: clear other object as we add them
    }

    public static void loadGame(FileInputStream inputStream) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            gameState = (Game) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
