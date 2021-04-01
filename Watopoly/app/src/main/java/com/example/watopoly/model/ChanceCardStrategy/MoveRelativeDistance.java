package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class MoveRelativeDistance extends ChanceCard {
    int numSpaces;

    public void executeAction(){
        Game gameState = Game.getInstance();
        int currPosition = gameState.getCurrentPlayer().getPosition();
        gameState.getCurrentPlayer().setPosition(currPosition + numSpaces);
    }

    public MoveRelativeDistance(String title, String description, int numSpaces){
        super(title, description);
        this.numSpaces = numSpaces;
    }

}
