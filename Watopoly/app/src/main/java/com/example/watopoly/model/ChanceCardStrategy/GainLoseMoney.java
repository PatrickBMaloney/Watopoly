package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class GainLoseMoney extends ChanceCard {
    private double amount;

    public void executeAction(){
        Game gameState = Game.getInstance();
        System.out.println("you gained some money");
        gameState.getCurrentPlayer().payAmount(amount * -1);
    }

    public GainLoseMoney(String title, String description, double amount){
        super(title, description);
        this.amount = amount;
    }
}
