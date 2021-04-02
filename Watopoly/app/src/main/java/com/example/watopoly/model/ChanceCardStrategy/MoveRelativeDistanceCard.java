package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class MoveRelativeDistanceCard implements ChanceCard {
    private int numSpaces;
    private String title;
    private String description;

    @Override
    public void executeAction(){
        Game gameState = Game.getInstance();
        int currPosition = gameState.getCurrentPlayer().getPosition();
        gameState.getCurrentPlayer().setPosition(currPosition + numSpaces);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public MoveRelativeDistanceCard(String title, String description, int numSpaces){
        this.title = title;
        this.description = description;
        this.numSpaces = numSpaces;
    }

}
