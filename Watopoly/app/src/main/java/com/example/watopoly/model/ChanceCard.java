package com.example.watopoly.model;

import java.io.Serializable;

public class ChanceCard implements Serializable {
    private String title;
    private String description;

    public ChanceCard(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
