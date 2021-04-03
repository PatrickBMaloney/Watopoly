package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.watopoly.R;
import com.example.watopoly.adapter.MortgagePropertyListAdapter;
import com.example.watopoly.adapter.PlayerInfoListAdapter;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.util.ArrayList;


public class MortgageActivity extends AppCompatActivity {

    private Game gameState = Game.getInstance();
    private PlayerInfoHeaderFragment playerInfoHeaderFragment;
    private RecyclerView mortgageRecyclerView;
    private MortgagePropertyListAdapter mortgageAdapter;
    Player myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);
        linkView();
        myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);

        linkView();
        setupAdapter();
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        mortgageRecyclerView = findViewById(R.id.mortgageRecyclerView);
        mortgageRecyclerView.setLayoutManager(layoutManager1);

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
    }

    private void setupAdapter() {
        ArrayList<Property> properties = myPlayer.getProperties();
        mortgageAdapter = new MortgagePropertyListAdapter(properties);
        mortgageRecyclerView.setAdapter(mortgageAdapter);
    }
}