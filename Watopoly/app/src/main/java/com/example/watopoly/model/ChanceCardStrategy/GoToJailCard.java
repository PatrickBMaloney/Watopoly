package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Jail;
import com.example.watopoly.model.Tile;
import com.example.watopoly.util.BoardTiles;

import java.util.ArrayList;

public class GoToJailCard implements ChanceStrategy {
    private String title;
    private String description;

    public void executeAction(){
        Game gameState = Game.getInstance();
        gameState.getCurrentPlayer().setJailed(true);
        gameState.getCurrentPlayer().setPosition(findIndexOfJailTile());
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public GoToJailCard(String title, String description){
        this.title = title;
        this.description = description;
    }

    private int findIndexOfJailTile(){
        ArrayList<Tile> tiles = BoardTiles.getTiles();
        for(int i = 0; i < tiles.size(); i++){
            if (tiles.get(i) instanceof Jail){ return i; }
        }
        return -1;
    }
}
