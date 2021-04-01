package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Jail;
import com.example.watopoly.util.BoardTiles;

public class GoToJail extends ChanceCard {

    public void executeAction(){
        Game gameState = Game.getInstance();
        gameState.getCurrentPlayer().setJailed(true);
        gameState.getCurrentPlayer().setPosition(BoardTiles.getTiles().indexOf(new Jail()));
    }

    public GoToJail(String title, String description){
        super(title, description);
    }
}
