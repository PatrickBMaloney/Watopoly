package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class GetOutOfJailCard extends ChanceCard {

    public void executeAction(){
        Game gameState = Game.getInstance();
        gameState.getCurrentPlayer().addJailFreeCard();
    }

    public GetOutOfJailCard(String title, String description){
        super(title, description);
    }
}
