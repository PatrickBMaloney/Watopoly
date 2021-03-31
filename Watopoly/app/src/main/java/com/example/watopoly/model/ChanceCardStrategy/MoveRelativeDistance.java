package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class MoveRelativeDistance extends ChanceCard {
    int numSpaces;

    public void executeAction(){
        Game gameState = Game.getInstance();
        System.out.println("you moved to a tile");
//        gameState.getCurrentPlayer().setPosition(numSpaces);
    }

    public MoveRelativeDistance(String title, String description, int numSpaces){
        super(title, description);
        this.numSpaces = numSpaces;
    }

}
