package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.watopoly.R;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;


public class MortgageActivity extends AppCompatActivity {

    private Game gameState = Game.getInstance();
    private PlayerInfoHeaderFragment playerInfoHeaderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);
        linkView();
        Player myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentChance);
    }
}