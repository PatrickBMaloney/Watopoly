package com.example.watopoly.model;

import com.example.watopoly.view.BoardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board implements Serializable {
    private ArrayList<Tile> boardTiles = new ArrayList<>();
    private transient BoardView boardView;

    private Map<Tile, ArrayList<Player>> drawingState = new HashMap<>();

    public void setBoardInfo(BoardView boardView, ArrayList<ChanceCard> cards, ArrayList<Player> players) {
        boardTiles = boardView.getTiles();
        this.boardView = boardView;
        setupDrawingState(players);
        setupCards(cards);
    }

    public Tile move(Player player, int steps) {
        int newPosition = (player.getPosition() + steps) % boardTiles.size();
        if (player.getPosition() + steps > boardTiles.size()) {
            player.passedGo();
        }

        // remove player from old Tile
        for (Tile tile: drawingState.keySet()) {
            ArrayList<Player> playerList = drawingState.get(tile);
            for (Iterator<Player> iterator = playerList.iterator(); iterator.hasNext(); ) {
                Player currPlayer = iterator.next();
                if (currPlayer.getName().equals(player.getName())) {
                    iterator.remove();
                    tile.decrementCurrNumberOfPlayers();
                }
            }
        }

        // calculate new Tile
        Tile newTile = boardTiles.get(newPosition);
        if (newTile.getCurrNumberOfPlayers() == newTile.getMaxNumberOfPlayers()) {
            newPosition = newPosition + 1;
            newTile = boardTiles.get(newPosition);
        }
        if (newTile.getName().equals("goToJail")) {
            newPosition = 9;
            newTile = boardTiles.get(newPosition);
        }

        // add player to new Tile
        newTile.incrementCurrNumberOfPlayers();
        player.setPosition(newPosition);
        newTile.landOn(player);
        drawingState.get(newTile).add(player);

        boardView.drawDrawingState(drawingState);
        return boardTiles.get(player.getPosition());
    }

    private void setupDrawingState(ArrayList<Player> players) {
        for (Tile t: boardTiles) {
            drawingState.put(t, new ArrayList<Player>());
        }

        boolean emptyDrawingState = true;
        for (Tile t: drawingState.keySet()) {
            ArrayList<Player> p = drawingState.get(t);
            if (p.size() > 0) {
                emptyDrawingState = false;
            }
        }

        if (emptyDrawingState) {
            for (Player p: players) {
                drawingState.get(boardTiles.get(0)).add(p);
            }
        }

        boardView.drawDrawingState(drawingState);
    }

    private void setupCards(ArrayList<ChanceCard> cards) {
        int jailPosition = 0;
        for (int x = 0; x < boardTiles.size(); x++) {
            if (boardTiles.get(x) instanceof Jail) {
                jailPosition = x;
                break;
            }
        }

        for (int x = 0; x < boardTiles.size(); x++) {
            if (boardTiles.get(x) instanceof GoToJail) {
                GoToJail goToJail = (GoToJail) boardTiles.get(x);
                goToJail.setJailLocation(jailPosition);
            }

            if (boardTiles.get(x) instanceof CardTile) {
                CardTile cardTile = (CardTile) boardTiles.get(x);
                cardTile.setCards(cards);

            }
        }

    }
}
