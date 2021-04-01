package com.example.watopoly.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;

import com.example.watopoly.enums.TileDirection;
import com.example.watopoly.view.BoardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {
    private ArrayList<Tile> boardTiles = new ArrayList<>();
    private transient BoardView boardView;

    private Map<Tile, ArrayList<Player>> drawingState = new HashMap<Tile, ArrayList<Player>>();

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

        Tile newTile = boardTiles.get(newPosition);
        player.setPosition(newPosition);
        newTile.landOn(player);
        drawingState.get(newTile).add(player);


        for (Tile t: drawingState.keySet()) {
            ArrayList<Player> players = drawingState.get(t);

            if (players.size() > 0) {
                System.out.print(t.name + " ");
                for (Player p: players) {
                    System.out.print(p.getName() + " ");
                }
                System.out.println();
            }

        }

        boardView.drawBitmap(drawingState);

        //boardView.drawBitmap(icon, newTile, player.getColour());
        //canvas.drawBitmap(icon, tile.getCoordinates().left,  tile.getCoordinates().top, paint);






        // handle multiple people
        //  use canvas to draw player bitmap
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
