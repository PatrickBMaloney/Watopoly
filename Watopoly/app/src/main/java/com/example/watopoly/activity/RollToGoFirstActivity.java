package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.watopoly.R;

public class RollToGoFirst extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_to_go_first);

        Button rollToGoFirstBtn = (Button) findViewById(R.id.rollToGoFirstBtn);
        rollToGoFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //TODO move this to a global function to be accessed for any dice-rolling purpose
            public void onClick(View v) {
                TextView diceRollTextView = (TextView) findViewById(R.id.diceRollTextView);

            int roll = diceRoll();
            diceRollTextView.setText(roll + " ");

            }
        });
    }

    public int diceRoll(){
        int num1 = (int)Math.random()*6+1;
        int num2 = (int)Math.random()*6+1;
        return num1+num2;

    }
}

