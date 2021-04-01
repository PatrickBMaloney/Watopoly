package com.example.watopoly.model;

import com.example.watopoly.view.BoardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {
    private ArrayList<Tile> boardTiles = new ArrayList<>();
    private transient BoardView boardView;

    private Map<Tile, ArrayList<Player>> drawingState = new HashMap<>();

    public void setBoardInfo(BoardView boardView, ArrayList<ChanceCard> cards, ArrayList<Player> players) {
        boardTiles = boardView.getTiles();
        this.boardView = boardView;
        setup(cards);

        for (Tile t: boardTiles) {
            drawingState.put(t, new ArrayList<Player>());
        }
        for (Player p: players) {
            drawingState.get(boardTiles.get(0)).add(p);
        }
        boardView.drawBitmap(drawingState);
    }

    public Tile move(Player player, int steps) {
        int newPosition = (player.getPosition() + steps) % boardTiles.size();
        if (player.getPosition() + steps > boardTiles.size()) {
            player.passedGo();
        }

        Tile oldTile = boardTiles.get(player.getPosition());
        drawingState.get(oldTile).remove(player);
        oldTile.decrementCurrNumberOfPlayers();

        Tile newTile = boardTiles.get(newPosition);
        if (newTile.getCurrNumberOfPlayers() == newTile.getMaxNumberOfPlayers()) {
            newPosition = newPosition + 1;
            newTile = boardTiles.get(newPosition);
        }
        newTile.incrementCurrNumberOfPlayers();
        player.setPosition(newPosition);
        newTile.landOn(player);
        drawingState.get(newTile).add(player);

        boardView.drawBitmap(drawingState);

        return boardTiles.get(player.getPosition());
    }

    private void setup(ArrayList<ChanceCard> cards) {
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
