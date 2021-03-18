package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.watopoly.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button testDiceRollBtn = (Button) findViewById(R.id.testDiceRollBtn);
        testDiceRollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diceRollIntent = new Intent(getApplicationContext(), RollToGoFirstActivity.class);
                startActivity(diceRollIntent);
            }
        });

        //REMOVE
        Intent intent = new Intent(this, EnterPlayerInfoActivity.class);
        intent.putExtra("numberOfPlayers", 1);
        startActivity(intent);
    }
}