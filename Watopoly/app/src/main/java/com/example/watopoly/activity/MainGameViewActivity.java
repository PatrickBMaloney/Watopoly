package com.example.watopoly.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.watopoly.R;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;

import java.util.ArrayList;

public class MainGameViewActivity extends AppCompatActivity {
    //TODO: move this somewhere else?
    private Game gameState = Game.getInstance();
    private static final double startingMoney = 500;

    private PlayerInfoHeaderFragment playerInfoHeaderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_view);
        linkView();
        setup();
        playerInfoHeaderFragment.setPlayer(gameState.nextTurn());

        //TODO: rolling
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);

        //TODO: bind button to the activity
        Button buyButton = findViewById(R.id.buyPropertyButton);
        buyButton.setVisibility(View.GONE);
        Button viewAssetButton = findViewById(R.id.viewAssetButton);
        Button tradeButton = findViewById(R.id.tradeButton);
        Button mortgageButton = findViewById(R.id.mortgageButton);
        Button endTurnButton = findViewById(R.id.endTurnButton);
    }

    private void setup() {
        Intent intent = getIntent();
        ArrayList<Integer> icons = intent.getIntegerArrayListExtra("icons");
        ArrayList<String> colours = intent.getStringArrayListExtra("colours");
        ArrayList<String> names = intent.getStringArrayListExtra("names");

        //TODO: set other metadata
        for (int x = 0; x < names.size(); x++) {
            Player player = new Player(names.get(x), startingMoney, colours.get(x), icons.get(x));
            gameState.addPlayer(player);
        }
    }
}
