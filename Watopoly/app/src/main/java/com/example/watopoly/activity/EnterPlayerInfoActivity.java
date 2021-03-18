package com.example.watopoly.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PlayerInfoListAdapter;

import java.util.ArrayList;

public class EnterPlayerInfoActivity extends AppCompatActivity {
    private PlayerInfoListAdapter shapeAdapter;
    private PlayerInfoListAdapter colourAdapter;
    private RecyclerView shapeRecyclerView;
    private RecyclerView colourRecyclerView;
    private TextView titleTextView;
    private EditText nameEditText;
    private Button enterPlayerInfoButton;
    private Dialog dialog;

    private int numberOfPlayers = 1;
    private int currentTurn = 1;

    private ArrayList<Integer> pathSelected = new ArrayList<>();
    private ArrayList<String> colourSelected = new ArrayList<>();
    private  ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_player_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        numberOfPlayers = intent.getIntExtra("numberOfPlayers", 1);

        linkView();
        setupAdapter();
        enterPlayerInfo();
    }

    private void linkView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        getSupportActionBar().hide();
        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
        shapeRecyclerView.setLayoutManager(layoutManager1);
        colourRecyclerView = findViewById(R.id.ColourOptionRecyclerView);
        colourRecyclerView.setLayoutManager(layoutManager2);
        titleTextView = findViewById(R.id.enterPlayerInfoTitleTextView);
        nameEditText = findViewById(R.id.nameEditText);
        enterPlayerInfoButton = findViewById(R.id.enterPlayerInfoButton);
        enterPlayerInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEntering();
            }
        });

        dialog = new Dialog(EnterPlayerInfoActivity.this,R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_next_player);
        Button nextPlayerButton = dialog.findViewById(R.id.nextPlayerButton);
        nextPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setupAdapter() {
        ArrayList<Integer> shapePath = new ArrayList<>();
        shapePath.add(R.drawable.anchor);
        shapePath.add(R.drawable.android);
        shapePath.add(R.drawable.film);
        shapePath.add(R.drawable.pet);

        ArrayList<String> shapeColour = new ArrayList<>();
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeAdapter = new PlayerInfoListAdapter(shapePath, shapeColour);
        shapeRecyclerView.setAdapter(shapeAdapter);

        ArrayList<Integer> colourPath = new ArrayList<>();
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);

        ArrayList<String> colours = new ArrayList<>();
        colours.add("#751A33");
        colours.add("#B34233");
        colours.add("#D28F33");
        colours.add("#D4B95E");
        colours.add("#4EA2A2");
        colours.add("#1A8693");
        colourAdapter = new PlayerInfoListAdapter(colourPath, colours);
        colourRecyclerView.setAdapter(colourAdapter);
    }

    private void enterPlayerInfo(){
        dialog.show();

        titleTextView.setText("Player "+currentTurn+ " Enter Info");
        nameEditText.setText("");
        colourAdapter.setSelected(-1);
        shapeAdapter.setSelected(-1);
        colourAdapter.notifyDataSetChanged();
        shapeAdapter.notifyDataSetChanged();
    }


    private void finishEntering(){
        //TODO: show error msg
        if (shapeAdapter.getSelected() == -1 || colourAdapter.getSelected() == -1 || nameEditText.getEditableText().toString().equals("")) { return; }

        //save data
        pathSelected.add(shapeAdapter.getPaths().get(shapeAdapter.getSelected()));
        colourSelected.add(colourAdapter.getColours().get(colourAdapter.getSelected()));
        names.add(nameEditText.getText().toString());

        shapeAdapter.getPaths().remove(shapeAdapter.getSelected());
        shapeAdapter.getColours().remove(shapeAdapter.getSelected());
        colourAdapter.getPaths().remove(colourAdapter.getSelected());
        colourAdapter.getColours().remove(colourAdapter.getSelected());
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
