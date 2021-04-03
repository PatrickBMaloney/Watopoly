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
    private RecyclerView unmortgageRecyclerView;
    private MortgagePropertyListAdapter unmortgageAdapter;
    Player myPlayer;
    private Button finishMortgageButton;
    private Button backToMainButton;
    private ArrayList<Property> mortProperties = new ArrayList<>();
    private ArrayList<Property> unmortProperties = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);
        linkView();
        myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);

        linkView();
        setupAdapter();
        mortgageProperties();
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        mortgageRecyclerView = findViewById(R.id.mortgageRecyclerView);
        mortgageRecyclerView.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        unmortgageRecyclerView = findViewById(R.id.unmortgageRecyclerView);
        unmortgageRecyclerView.setLayoutManager(layoutManager2);

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);

        finishMortgageButton = findViewById(R.id.finishMortgageButton);
        finishMortgageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishMortgaging();
            }
        });

        backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupAdapter() {
        ArrayList<Property> allProperties = myPlayer.getProperties();
        for (int i=0; i<allProperties.size(); i++){
            if (allProperties.get(i).getMortgaged() == false){
                mortProperties.add(allProperties.get(i));
            } else {
                unmortProperties.add(allProperties.get(i));
            }
        }

        mortgageAdapter = new MortgagePropertyListAdapter(mortProperties);
        mortgageRecyclerView.setAdapter(mortgageAdapter);

        unmortgageAdapter = new MortgagePropertyListAdapter(unmortProperties);
        unmortgageRecyclerView.setAdapter(unmortgageAdapter);
    }

    private void mortgageProperties(){
        mortgageAdapter.clearSelected();
        mortgageAdapter.notifyDataSetChanged();
        unmortgageAdapter.clearSelected();
        unmortgageAdapter.notifyDataSetChanged();
    }

    private void finishMortgaging(){
        ArrayList<Integer> mortgageList = mortgageAdapter.getSelected();
        ArrayList<Integer> unmortageList = unmortgageAdapter.getSelected();
        if (mortgageList.size() == 0 && unmortageList.size() == 0) { return; }

        for (int i =0; i<mortgageList.size(); i++){
            Property selectedProperty = mortProperties.get(mortgageList.get(i));
            selectedProperty.mortgage();
            myPlayer.receiveAmount(selectedProperty.getPurchasePrice());
        }

        for (int i =0; i<unmortageList.size(); i++){
            Property selectedProperty = unmortProperties.get(unmortageList.get(i));
            selectedProperty.unMortgage();
            myPlayer.payAmount(selectedProperty.getPurchasePrice());
        }

        playerInfoHeaderFragment.refresh();
        finish();
    }

}