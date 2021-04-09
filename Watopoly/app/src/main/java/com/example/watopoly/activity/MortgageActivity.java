package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.watopoly.R;
import com.example.watopoly.adapter.CellPropertyListAdapter;
import com.example.watopoly.adapter.MortgagePropertyListAdapter;
import com.example.watopoly.adapter.PlayerInfoListAdapter;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.util.ArrayList;
import java.util.List;


public class MortgageActivity extends AppCompatActivity {

    private RecyclerView mortgageRecyclerView;
    private CellPropertyListAdapter mortgageAdapter;
    private RecyclerView unmortgageRecyclerView;
    private CellPropertyListAdapter unmortgageAdapter;
    Player myPlayer;
    private Button finishMortgageButton;
    private Button backToMainButton;
    private ArrayList<Property> mortProperties = new ArrayList<>();
    private ArrayList<Property> unmortProperties = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);

        Game gameState = Game.getInstance();
        myPlayer = gameState.getCurrentPlayer();

        linkView();
        setupAdapter();
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mortgageRecyclerView = findViewById(R.id.mortgageRecyclerView);
        mortgageRecyclerView.setLayoutManager(layoutManager1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mortgageRecyclerView.getContext(),  layoutManager1.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.empty_divider));
        mortgageRecyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        unmortgageRecyclerView = findViewById(R.id.unmortgageRecyclerView);
        unmortgageRecyclerView.setLayoutManager(layoutManager2);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(unmortgageRecyclerView.getContext(),  layoutManager2.getOrientation());
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(this, R.drawable.empty_divider));
        unmortgageRecyclerView.addItemDecoration(dividerItemDecoration2);

        FragmentManager fm = getSupportFragmentManager();
        PlayerInfoHeaderFragment playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
        playerInfoHeaderFragment.setPlayer(myPlayer);

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

        mortgageAdapter = new CellPropertyListAdapter(mortProperties, false, null);
        mortgageRecyclerView.setAdapter(mortgageAdapter);
        mortgageAdapter.notifyDataSetChanged();

        unmortgageAdapter = new CellPropertyListAdapter(unmortProperties, false, null);
        unmortgageRecyclerView.setAdapter(unmortgageAdapter);
        unmortgageAdapter.notifyDataSetChanged();
    }

    private void finishMortgaging(){
        List<Boolean> mortgageList = mortgageAdapter.getSelected();
        List<Boolean> unmortgageList = unmortgageAdapter.getSelected();

        for (int i = 0; i < mortgageList.size(); i++){
            if (mortgageList.get(i)) {
                Property selectedProperty = mortProperties.get(i);
                selectedProperty.mortgage();
                myPlayer.receiveAmount(selectedProperty.getPurchasePrice()/2);
            }
        }

        for (int i = 0; i < unmortgageList.size(); i++) {
            if (unmortgageList.get(i)) {
                Property selectedProperty = unmortProperties.get(i);
                selectedProperty.unMortgage();
                myPlayer.payAmount(selectedProperty.getPurchasePrice()/2);
            }
        }

        finish();
    }

}