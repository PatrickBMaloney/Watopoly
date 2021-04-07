package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Tile;
import com.example.watopoly.util.BoardTiles;

public class MoveToTileCard implements ChanceStrategy {
    private Tile tile;
    private String title;
    private String description;

    @Override
    public void executeAction(){
        Game gameState = Game.getInstance();
        int newPosition = BoardTiles.getTiles().indexOf(tile);
        gameState.getCurrentPlayer().setPosition(newPosition);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public MoveToTileCard(String title, String description, Tile tile){
        this.title = title;
        this.description = description;
        this.tile = tile;
    }
}
