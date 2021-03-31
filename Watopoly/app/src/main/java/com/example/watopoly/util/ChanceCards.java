package com.example.watopoly.util;

import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.ChanceCardStrategy.GainLoseMoney;
import com.example.watopoly.model.ChanceCardStrategy.GoToJail;
import com.example.watopoly.model.ChanceCardStrategy.MoveRelativeDistance;
import com.example.watopoly.model.ChanceCardStrategy.MoveToTile;
import com.example.watopoly.model.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public final class ChanceCards {

    private ArrayList<Tile> tiles = BoardTiles.getTiles();
    private static ArrayList<ChanceCard> chanceCards = new ArrayList<>(Arrays.asList(
        new GainLoseMoney("It's time to be fees arranged.", "Next school term is on the horizon. It's time to pay your tuition on Quest! Lose $100", -100),
        new GainLoseMoney("You won coop student of the year!", "Congrats on your achievement! You get a picture with Feridun and a $20 award.", 20),
        new GainLoseMoney("You got stuck with a bad landlord.", "Pay $50 for repairs.", 50),
        new GoToJail("You have been caught violating policy 71", "Go to jail! (tsk tsk)"),
        new GainLoseMoney("Congrats! Your team has won one of the intramurals categories", "Way to go! Collect 50", 50),
        new MoveRelativeDistance("An unprovoked goose is chasing you for some reason.", "Your flight or fight response activates and you start running. Move 5 spaces", 5),
        new GainLoseMoney("You got food poisoning from UW food services.", "It happens to the best of us. Pay $50 to visit the doctor.", -50),
        new GainLoseMoney("You just got a grant from OSAP", "Collect 200", 200),
        new MoveToTile("It's that time of year. You need to take a basic Linkedin photo.", "Move to E7 to take a bridge picture", BoardTiles.getBuildingTileByName("E7")),
        new GainLoseMoney("You got a refund for your WUSA dental/health plan", "Collect 100", 100),
        new GoToJail("The librarian has caught you red handed whispering in silent study!", "You get aggressively shushed and sent to jail. Literally how could you."),
        new MoveToTile("You hear they're giving out free food in the Arts Quad", "Of course you'll be the first one in line! Move to ML", BoardTiles.getBuildingTileByName("ML")),
        new MoveToTile("You have an exam tomorrow and its looking like an all nighter.", "Move to DC", BoardTiles.getBuildingTileByName("DC")),
        new MoveRelativeDistance("A goose on the sidewalk is staring you down.", "Move back 3 spaces to avoid eminent danger", -3),
        new GainLoseMoney("You lost your WatCard on the bus", "Pay $50 to replace it.", -50),
        new GainLoseMoney("To your dismay, C&D does not accept meal plan money for some reason", "Pay $20 for that Jamaican Patty", 20),
        new GoToJail("You didn't know that one section of QNC was only for grad students", "The second you took a step onto the premise, security has you removed and put in jail"),
        new MoveToTile("You had Lazeez for dinner and you're beginning to feel it.", "Good luck with that. Move to C&D to pick up some Peptobismol", BoardTiles.getBuildingTileByName("Math C&D")),
        new GainLoseMoney("Give into your bubble tea addiction.", "You have no choice but to enjoy a delicious milk tea from your favourite bubble tea shop. Spend $20", 20),
        new GainLoseMoney("Geese have stolen your lunch money.", "On your way to 8:30 class some geese attacked you on V1 green and took your money. Lose $20", -20)));

    public static ArrayList<ChanceCard> getAllChanceCards(){
        return chanceCards;
    }
    public static ChanceCard drawRandomChanceCard(){
        Random rand = new Random();
        return chanceCards.get(rand.nextInt(chanceCards.size()));
    }
}

