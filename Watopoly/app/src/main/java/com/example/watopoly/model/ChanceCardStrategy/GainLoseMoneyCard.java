package com.example.watopoly.model.ChanceCardStrategy;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;

public class GainLoseMoneyCard implements ChanceCard {
    private double amount;
    private String title;
    private String description;

    @Override
    public void executeAction(){
        Game gameState = Game.getInstance();
        if(amount > 0){
            gameState.getCurrentPlayer().receiveAmount(amount);
        }
        else {
            gameState.getCurrentPlayer().payAmount(amount * -1);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public GainLoseMoneyCard(String title, String description, double amount){
        this.title = title;
        this.description = description;
        this.amount = amount;
    }
}
