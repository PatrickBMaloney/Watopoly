package com.example.watopoly.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.watopoly.R;
import com.example.watopoly.fragment.DiceRollFragment;
import com.example.watopoly.fragment.FragmentCallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RollToGoFirstActivity extends AppCompatActivity implements FragmentCallbackListener {
    private ImageView playerIconImageView;
    private TextView diceRollResultTextView;
    private TextView playerRollingTextView;
    private Button nextPlayerDiceRollButton;
    private DiceRollFragment diceRollFragment;

    private ArrayList<Integer> rollValues = new ArrayList<>();
    RequestQueue queue;
    Context c;

    int id = -1;
    int icon = -1;
    String colour = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_to_go_first);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        icon = intent.getIntExtra("icon", -1);
        colour = intent.getStringExtra("colour");
        name = intent.getStringExtra("name");

        System.out.println(id);
        System.out.println(icon);
        System.out.println(colour);
        System.out.println(name);


        queue = Volley.newRequestQueue(this);
        c = this;


        linkView();
        playerIconImageView.setImageResource(icon);
        ImageViewCompat.setImageTintMode(playerIconImageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(playerIconImageView, ColorStateList.valueOf(Color.parseColor(colour)));

        pollForPlayer();
    }

    private void pollForPlayer() {
        String url = "https://cs446-server.ue.r.appspot.com/room";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        System.out.println("room");

                        try {
                            if (response.getInt("curr_player_turn") == id) {
                                playerRollingTextView.setText("Your turn to roll!");
                                diceRollFragment.setRollButtonVisibility(View.VISIBLE);
                                pollForPlayer();
                            }
                            else if (response.getInt("curr_player_turn") > response.getInt("max_players")) {
                                playerRollingTextView.setText("Done!");
                                diceRollFragment.setRollButtonVisibility(View.INVISIBLE);
                                System.out.println("exit");
                                Intent intent = new Intent(c, DisplayPlayerOrderActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("icon", icon);
                                intent.putExtra("colour", colour);
                                intent.putExtra("name", name);
                                startActivity(intent);
                            }
                            else {
                                playerRollingTextView.setText("Waiting for other players to roll...");
                                diceRollFragment.setRollButtonVisibility(View.INVISIBLE);
                                pollForPlayer();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);

    }


    private void setDiceRoll(int diceRoll) {

        String url = "https://cs446-server.ue.r.appspot.com/set-dice-roll";

        JSONObject hostData = new JSONObject();
        try {
            hostData.put("Id", id);
            hostData.put("InitialRoll", diceRoll);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, hostData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        playerIconImageView = findViewById(R.id.playerIconImageView);
        diceRollResultTextView = findViewById(R.id.diceRollResultTextView);
        playerRollingTextView = findViewById(R.id.playerRollingTextView);
        diceRollResultTextView.setText("?");

        FragmentManager fm = getSupportFragmentManager();
        diceRollFragment= (DiceRollFragment) fm.findFragmentById(R.id.diceRollOrderFragment);
        diceRollFragment.setCallbackListener(this);
    }

    private void finishRolling(){
//        if (currentTurn == numberOfPlayers-1) {
//            Log.d("Rolls",rollValues.toString());
//            Intent playerOrderIntent = new Intent(getApplicationContext(), DisplayPlayerOrderActivity.class);
//            playerOrderIntent.putExtra("rolls", rollValues);
//            playerOrderIntent.putExtra("names", names);
//            playerOrderIntent.putExtra("icons", icons);
//            playerOrderIntent.putExtra("colours", colours);
//            startActivity(playerOrderIntent);
//        } else {
//            currentTurn++;
//            playerDiceRoll();
//        }
    }

    @Override
    public void onCallback() {
        int diceRollResult = diceRollFragment.getDiceRollResult();
        rollValues.add(diceRollResult);
        diceRollResultTextView.setText(""+diceRollResult);
        setDiceRoll(diceRollResult);

        diceRollFragment.setRollButtonVisibility(View.INVISIBLE);
    }
}

