package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Tile;

public class MoveToTile extends ChanceCard {
    private Tile tile;

    public void executeAction(){
        Game gameState = Game.getInstance();
        System.out.println("you moved to a tile");
        gameState.moveCurrentPlayer(5);
    }

    public MoveToTile(String title, String description, Tile tile){
        super(title, description);
        this.tile = tile;
    }
}
