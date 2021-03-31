package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class MoveRelativeDistance extends ChanceCard {
    int numSpaces;

    public void executeAction(){
        Game gameState = Game.getInstance();
        if(numSpaces > 0) {
            gameState.moveCurrentPlayer(numSpaces);
        }
        else { //moving backwards
            gameState.moveCurrentPlayer(38 + numSpaces);
            if (gameState.getCurrentPlayer().getPosition() != 0) {
                gameState.getCurrentPlayer().payAmount(200); //remove money they earned from passing go
            }
        }
    }

    public MoveRelativeDistance(String title, String description, int numSpaces){
        super(title, description);
        this.numSpaces = numSpaces;
    }

}
