package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.RollToGoFirstAdaptor;

import java.util.ArrayList;

public class RollToGoFirstActivity extends AppCompatActivity {

//    Get info from "Enter Player Info"
    private ArrayList<Integer> pathSelected;
    private ArrayList<String> colourSelected;
    private ArrayList<String> names;

    private RollToGoFirstAdaptor shapeAdapter;
    private RecyclerView shapeRecyclerView;

    private int numberOfPlayers;
    private int currentTurn = 1;
    private int roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_to_go_first);
        getSupportActionBar().hide();

        pathSelected = getIntent().getIntegerArrayListExtra("pathSelected");
        colourSelected = getIntent().getStringArrayListExtra("colourSelected");
        names = getIntent().getStringArrayListExtra("names");
        numberOfPlayers = names.size();

        linkView();

        Button rollToGoFirstBtn = (Button) findViewById(R.id.rollToGoFirstBtn);
        Button nextPlayerDiceRollButton = (Button) findViewById(R.id.nextPlayerDiceRollButton);
        nextPlayerDiceRollButton.setClickable(false);

        final ImageView dice1 = (ImageView) findViewById(R.id.dice1ImageView);
        final ImageView dice2 = (ImageView) findViewById(R.id.dice2ImageView);
        rollToGoFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO move this to a global function to be accessed for any dice-rolling purpose
                int num1 = (int)(Math.random()*6+1);
                int num2 = (int)(Math.random()*6+1);
                roll = num1+num2;

                shapeAdapter = new RollToGoFirstAdaptor(pathSelected, colourSelected, roll);
                shapeRecyclerView.setAdapter(shapeAdapter);
                Drawable dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice4, null);
                Drawable dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice4, null);

                switch (num1) {
                    case 1:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice1, null);
                        break;
                    case 2:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice2, null);
                        break;
                    case 3:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice3, null);
                        break;
                    case 4:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice4, null);
                        break;
                    case 5:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice5, null);
                        break;
                    case 6:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice6, null);
                        break;
                }

                switch (num2) {
                    case 1:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice1, null);
                        break;
                    case 2:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice2, null);
                        break;
                    case 3:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice3, null);
                        break;
                    case 4:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice3, null);
                        break;
                    case 5:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice5, null);
                        break;
                    case 6:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice6, null);
                        break;
                }
                dice1.setImageDrawable(dice1File);
                dice2.setImageDrawable(dice2File);
                nextPlayerDiceRollButton.setClickable(true);
                rollToGoFirstBtn.setClickable(false);

            }
        });
        nextPlayerDiceRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRolling();
            }
        });

    }

    private void linkView(){
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
        shapeRecyclerView.setLayoutManager(layoutManager1);
        shapeAdapter = new RollToGoFirstAdaptor(pathSelected, colourSelected);
        shapeRecyclerView.setAdapter(shapeAdapter);
    }


    private void finishRolling(){
//        //save data
//        pathSelected.add(shapeAdapter.getPaths().get(shapeAdapter.getSelected()));
//        colourSelected.add(colourAdapter.getColours().get(colourAdapter.getSelected()));
//        names.add(nameEditText.getText().toString());
//
//        shapeAdapter.getPaths().remove(shapeAdapter.getSelected());
//        shapeAdapter.getColours().remove(shapeAdapter.getSelected());
//        colourAdapter.getPaths().remove(colourAdapter.getSelected());
//        colourAdapter.getColours().remove(colourAdapter.getSelected());
        if (currentTurn == numberOfPlayers) {
            //Pass to next activity
            Log.d("Test ", pathSelected.toString());
            Log.d("Test ", colourSelected.toString());
            Log.d("Test ", names.toString());
            Intent diceRollIntent = new Intent(getApplicationContext(), RollToGoFirstActivity.class);
            diceRollIntent.putExtra("pathSelected", pathSelected);
            diceRollIntent.putExtra("colourSelected", colourSelected);
            diceRollIntent.putExtra("names", names);
            startActivity(diceRollIntent);
        } else {
            currentTurn ++;
            enterPlayerInfo();
        }
    }
}

