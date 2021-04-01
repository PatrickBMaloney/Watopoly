package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.Board;
import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Tile;
import com.example.watopoly.util.BoardTiles;

public class MoveToTile extends ChanceCard {
    private Tile tile;

    public void executeAction(){
        Game gameState = Game.getInstance();
        int newPosition = BoardTiles.getTiles().indexOf(tile);
        gameState.getCurrentPlayer().setPosition(newPosition);
    }

    public MoveToTile(String title, String description, Tile tile){
        super(title, description);
        this.tile = tile;
    }
}
