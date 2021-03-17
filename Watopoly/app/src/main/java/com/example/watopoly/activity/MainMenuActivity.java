package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.watopoly.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //REMOVE
        Intent intent = new Intent(this, EnterPlayerInfoActivity.class);
        intent.putExtra("numberOfPlayers", 4);
        startActivity(intent);
    }
}