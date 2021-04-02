package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class GetOutOfJailCard implements ChanceStrategy {
    private String title;
    private String description;

    public void executeAction(){
        Game gameState = Game.getInstance();
        gameState.getCurrentPlayer().addJailFreeCard();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public GetOutOfJailCard(String title, String description){
        this.title = title;
        this.description = description;
    }
}
