package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.Board;
import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Jail;
import com.example.watopoly.model.Tile;
import com.example.watopoly.util.BoardTiles;

import java.util.ArrayList;

public class GoToJail extends ChanceCard {

    public void executeAction(){
        Game gameState = Game.getInstance();
        gameState.getCurrentPlayer().setJailed(true);
        gameState.getCurrentPlayer().setPosition(findIndexOfJailTile());
    }

    public GoToJail(String title, String description){
        super(title, description);
    }

    private int findIndexOfJailTile(){
        ArrayList<Tile> tiles = BoardTiles.getTiles();
        for(int i = 0; i < tiles.size(); i++){
            if (tiles.get(i) instanceof Jail){ return i; }
        }
        return -1;
    }
}
