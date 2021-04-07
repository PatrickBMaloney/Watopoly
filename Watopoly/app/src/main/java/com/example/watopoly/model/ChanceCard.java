package com.example.watopoly.model;

import com.example.watopoly.model.ChanceCardStrategy.ChanceStrategy;

public class ChanceCard {
    private ChanceStrategy strategy;

    public ChanceCard(){}

    public void setStrat(ChanceStrategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(){
        strategy.executeAction();
    }
}
