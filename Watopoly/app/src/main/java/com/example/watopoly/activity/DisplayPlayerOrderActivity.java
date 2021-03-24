package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.watopoly.R;
import com.example.watopoly.adapter.RollToGoFirstAdaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DisplayPlayerOrderActivity extends AppCompatActivity {
    private ArrayList<Integer> icons = new ArrayList<>();
    private ArrayList<String> colours = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> rollValues = new ArrayList<>();

    private RollToGoFirstAdaptor shapeAdapter;
    private RecyclerView shapeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_player_order);

        setupOrdering();
        linkView();
        Button passToFirstPlayerBtn = findViewById(R.id.passToFirstPlayerBtn);
        passToFirstPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainGameViewActivity.class);
                intent.putExtra("icons", icons);
                intent.putExtra("colours", colours);
                intent.putExtra("names", names);
                startActivity(intent);
            }
        });
    }

    private void setupOrdering() {
        ArrayList<Integer> tempIcons = getIntent().getIntegerArrayListExtra("icons");
        ArrayList<String> tempColours = getIntent().getStringArrayListExtra("colours");
        ArrayList<String> tempNames = getIntent().getStringArrayListExtra("names");
        ArrayList<Integer> tempRollValues = getIntent().getIntegerArrayListExtra("rolls");

        ArrayList<Pair<Integer, Integer>> ordering = new ArrayList<>();
        for (int x = 0; x < tempRollValues.size(); x++) { ordering.add(new Pair<Integer, Integer>(tempRollValues.get(x), x)); }
        Collections.sort(ordering, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o1.first > o2.first ? -1 : 1;
            }
        });

        for (Pair<Integer, Integer> pair: ordering) {
            colours.add(tempColours.get(pair.second));
            icons.add(tempIcons.get(pair.second));
            names.add(tempNames.get(pair.second));
            rollValues.add(tempRollValues.get(pair.second));
        }
    }

    private void linkView(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
        shapeRecyclerView.setLayoutManager(layoutManager1);
        shapeAdapter = new RollToGoFirstAdaptor(icons, colours, rollValues);
        shapeRecyclerView.setAdapter(shapeAdapter);
    }
}