package com.example.watopoly.util;

import com.example.watopoly.R;
import com.example.watopoly.enums.TileDirection;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.CardTile;
import com.example.watopoly.model.GoTile;
import com.example.watopoly.model.GoToJail;
import com.example.watopoly.model.Jail;
import com.example.watopoly.model.Parking;
import com.example.watopoly.model.Railway;
import com.example.watopoly.model.TaxTile;
import com.example.watopoly.model.Tile;
import com.example.watopoly.model.Utility;

import java.util.ArrayList;
import java.util.Arrays;

public final class BoardTiles {

    private static ArrayList<Tile> boardTiles = new ArrayList<>(Arrays.asList(
            new GoTile(),
            new Building("CAMJ", TileDirection.LEFT, 2, 60, 50,"#955436"),
            new CardTile("Community Chest", TileDirection.LEFT),
            new Building("Earth Sci", TileDirection.LEFT, 4, 60, 50,"#955436"),
            new TaxTile(TileDirection.LEFT),
            new Railway("Ion Train", TileDirection.LEFT, 25, 200, R.drawable.ion),
            new Building("CPH", TileDirection.LEFT, 6, 100, 50,"#AAE0FA"),
            new CardTile("Chance", TileDirection.LEFT),
            new Building("DWE", TileDirection.LEFT, 8, 120, 50,"#AAE0FA"),
            new Jail(),
            new Building("RCH", TileDirection.TOP, 10, 140, 100, "#D93A96"),
            new Utility("Eng C&D", TileDirection.TOP, 0, 150),
            new Building("ALH", TileDirection.TOP, 10, 140, 100,"#D93A96"),
            new Building("HH", TileDirection.TOP, 12, 160, 100,"#D93A96"),
            new Railway("GRT", TileDirection.TOP, 25, 200, R.drawable.grt),
            new Building("PAS", TileDirection.TOP, 14, 180, 100,"#F7941D"),
            new CardTile("Community Chest", TileDirection.TOP),
            new Building("EV2", TileDirection.TOP, 14, 180, 100,"#F7941D"),
            new Building("EV3", TileDirection.TOP, 16, 200, 100, "#F7941D"),
            new Parking(),
            new Building("ML", TileDirection.RIGHT, 18, 220, 150, "#ED1B24"),
            new CardTile("Chance", TileDirection.RIGHT),
            new Building("Needles Hall", TileDirection.RIGHT, 18, 220, 150,"#ED1B24"),
            new Building("STC", TileDirection.RIGHT, 20, 240, 150,"#ED1B24"),
            new Railway("UW Shuttle", TileDirection.RIGHT, 25, 200, R.drawable.uw_shuttle),
            new Building("QNC", TileDirection.RIGHT, 22, 260, 150,"#FEF200"),
            new Utility("Math C&D", TileDirection.RIGHT, 0, 150),
            new Building("PAC", TileDirection.RIGHT, 24, 280, 150,"#FEF200"),
            new GoToJail(),
            new Building("AHS", TileDirection.BOTTOM, 26, 300, 200,"#1FB25A"),
            new Building("BMH", TileDirection.BOTTOM, 26, 300, 200,"#1FB25A"),
            new CardTile("Community Chest", TileDirection.BOTTOM),
            new Building("M3", TileDirection.BOTTOM, 28, 320, 200,"#1FB25A"),
            new Railway("FedBus", TileDirection.BOTTOM, 25, 200, R.drawable.fedbus),
            new CardTile("Chance", TileDirection.BOTTOM),
            new Building("DC", TileDirection.BOTTOM, 35, 350, 200,"#0072BB"),
            new TaxTile(TileDirection.BOTTOM),
            new Building("E7", TileDirection.BOTTOM, 50, 400, 200,"#0072BB")));

    public static ArrayList<Tile> getTiles() {
        return boardTiles;
    }
}