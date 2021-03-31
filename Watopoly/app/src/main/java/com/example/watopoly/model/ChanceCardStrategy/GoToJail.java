package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class GoToJail extends ChanceCard {

    public void executeAction(){
        Game gameState = Game.getInstance();
        gameState.getCurrentPlayer().setJailed(true);
    }

    public GoToJail(String title, String description){
        super(title, description);
    }
}
