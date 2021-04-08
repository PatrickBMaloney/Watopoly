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
import com.example.watopoly.model.TileFactory;
import com.example.watopoly.model.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class BoardTiles {

    private static ArrayList<Tile> boardTiles = new ArrayList<>(Arrays.asList(
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Go)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building,"CAMJ", TileDirection.LEFT, 2, 60,"#955436")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Chance, "Community Chest", TileDirection.LEFT)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "Earth Sci", TileDirection.LEFT, 4, 60,"#955436")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Tax, TileDirection.LEFT)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Railway, "Ion Train", TileDirection.LEFT, 25, 200)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "CPH", TileDirection.LEFT, 6, 100,"#AAE0FA")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Chance, "Chance", TileDirection.LEFT)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "DWE", TileDirection.LEFT, 8, 120,"#AAE0FA")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Jail)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "RCH", TileDirection.TOP, 10, 140, "#D93A96")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Utility, "Eng C&D", TileDirection.TOP, 0, 150)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "ALH", TileDirection.TOP, 10, 140,"#D93A96")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "HH", TileDirection.TOP, 12, 160,"#D93A96")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Railway, "GRT", TileDirection.TOP, 25, 200)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "PAS", TileDirection.TOP, 14, 180,"#F7941D")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Chance, "Community Chest", TileDirection.TOP)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "EV2", TileDirection.TOP, 14, 180,"#F7941D")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "EV3", TileDirection.TOP, 16, 200, "#F7941D")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Parking)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "ML", TileDirection.RIGHT, 18, 220, "#ED1B24")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Chance, "Chance", TileDirection.RIGHT)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "Needles Hall", TileDirection.RIGHT, 18, 220,"#ED1B24")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "STC", TileDirection.RIGHT, 20, 240,"#ED1B24")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Railway, "UW Shuttle", TileDirection.RIGHT, 25, 200)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "QNC", TileDirection.RIGHT, 22, 260,"#FEF200")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Utility, "Math C&D", TileDirection.RIGHT, 0, 150)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "PAC", TileDirection.RIGHT, 24, 280,"#FEF200")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.GoToJail)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "AHS", TileDirection.BOTTOM, 26, 300,"#1FB25A")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "BMH", TileDirection.BOTTOM, 26, 300,"#1FB25A")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Chance, "Community Chest", TileDirection.BOTTOM)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "M3", TileDirection.BOTTOM, 28, 320,"#1FB25A")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Railway, "FedBus", TileDirection.BOTTOM, 25, 200)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Chance, "Chance", TileDirection.BOTTOM)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "DC", TileDirection.BOTTOM, 35, 350,"#0072BB")),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Tax, TileDirection.BOTTOM)),
            TileFactory.createTile(new TileFactory.TileInitInfo(TileFactory.TileType.Building, "E7", TileDirection.BOTTOM, 50, 400,"#0072BB"))));

    public static ArrayList<Tile> getTiles() {
        return boardTiles;
    }

    public static Tile getBuildingTileByName(String tileName){
        for(Tile currentTile:boardTiles){
            if((currentTile instanceof Building || currentTile instanceof Utility ) && currentTile.getName().equals(tileName)){
                return currentTile;
            }
        }
        return null;
    }

    public static ArrayList<Tile> getTilesByColor(String color){
        List<Tile> result = new ArrayList<>();
        for (Tile currentTile: boardTiles) {
            if()
        }
    }
}
