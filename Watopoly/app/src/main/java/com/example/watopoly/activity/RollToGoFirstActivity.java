package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watopoly.R;

public class RollToGoFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_to_go_first);

        Button rollToGoFirstBtn = (Button) findViewById(R.id.rollToGoFirstBtn);
        final ImageView dice1 = (ImageView) findViewById(R.id.dice1ImageView);
        final ImageView dice2 = (ImageView) findViewById(R.id.dice2ImageView);
        rollToGoFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView diceRollTextView = (TextView) findViewById(R.id.diceRollTextView);

                //TODO move this to a global function to be accessed for any dice-rolling purpose
                int num1 = (int)(Math.random()*6+1);
                int num2 = (int)(Math.random()*6+1);
                int roll = num1+num2;
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

//                int imageResource1 = getResources().getIdentifier(dice1File, null, );
                dice1.setImageDrawable(dice1File);
                dice2.setImageDrawable(dice2File);
//                int imageResource2 = getResources().getIdentifier(dice2File, null, this.getPackageName());
//                dice2.setImageResource(imageResource2);
                diceRollTextView.setText(roll + " ");

            }
        });
    }
}

