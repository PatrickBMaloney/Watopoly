package com.example.watopoly.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.watopoly.R;

public class DiceRollFragment extends Fragment {
    private FragmentCallbackListener callbackListener;
    private int diceRollResult = 0;
    private Button rollDiceButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice_roll,container,false);

        final ImageView dice1ImageView = view.findViewById(R.id.dice1ImageView);
        final ImageView dice2ImageView = view.findViewById(R.id.dice2ImageView);
        rollDiceButton = view.findViewById(R.id.rollDiceButton);
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = (int)(Math.random()*6+1);
                int num2 = (int)(Math.random()*6+1);

                diceRollResult = num1 + num2;
                dice1ImageView.setImageResource(getDiceImageResource(num1));
                dice2ImageView.setImageResource(getDiceImageResource(num2));

                if (callbackListener != null) {
                    callbackListener.onCallback();
                }
            }
        });
        return view;
    }

    private int getDiceImageResource(int roll){
        int result = 0;
        switch (roll) {
            case 1:
                result = R.drawable.dice1;
                break;
            case 2:
                result = R.drawable.dice2;
                break;
            case 3:
                result = R.drawable.dice3;
                break;
            case 4:
                result = R.drawable.dice4;
                break;
            case 5:
                result = R.drawable.dice5;
                break;
            default:
                result = R.drawable.dice6;
        }
        return result;
    }

    public void setCallbackListener(FragmentCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    public int getDiceRollResult() {
        return diceRollResult;
    }

    public void setRollButtonHidden(boolean isHidden) {
        rollDiceButton.setVisibility( isHidden ? View.GONE : View.VISIBLE);
    }
}
