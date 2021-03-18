package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.watopoly.R;
import com.example.watopoly.adapter.RollToGoFirstAdaptor;

import java.util.ArrayList;

public class DisplayPlayerOrderActivity extends AppCompatActivity {


    private ArrayList<Integer> icons;
    private ArrayList<String> colours;
    private ArrayList<String> names;
    private ArrayList<Integer> rollValues;
    private RollToGoFirstAdaptor shapeAdapter;
    private RecyclerView shapeRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_player_order);
        getSupportActionBar().hide();

        icons = getIntent().getIntegerArrayListExtra("icons");
        colours = getIntent().getStringArrayListExtra("colours");
        names = getIntent().getStringArrayListExtra("names");
        rollValues = getIntent().getIntegerArrayListExtra("rolls");

        linkView();
        Button passToFirstPlayerBtn = (Button) findViewById(R.id.passToFirstPlayerBtn);
        passToFirstPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Go to next Activity
            }
        });
    }

        private void linkView(){
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
        shapeRecyclerView.setLayoutManager(layoutManager1);
        shapeAdapter = new RollToGoFirstAdaptor(icons, colours, rollValues);
        shapeRecyclerView.setAdapter(shapeAdapter);
    }
}