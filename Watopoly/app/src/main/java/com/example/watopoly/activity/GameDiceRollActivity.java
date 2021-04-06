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
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class GameDiceRollActivity extends AppCompatActivity implements FragmentCallbackListener {
    private DiceRollFragment diceRollFragment;


    int id = -1;
    int icon = -1;
    String colour = "";
    String name = "";
    Context c;
    RequestQueue queue2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dice_roll);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        icon = intent.getIntExtra("icon", -1);
        colour = intent.getStringExtra("colour");
        name = intent.getStringExtra("name");
        c = this;

        ImageView iv = findViewById(R.id.playerIconImageView);
        iv.setImageResource(icon);
        ImageViewCompat.setImageTintMode(iv, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(iv, ColorStateList.valueOf(Color.parseColor(colour)));

        TextView name_tv = findViewById(R.id.playerNameTextView);
        name_tv.setText(name);

        textView = findViewById(R.id.actionTv);

        queue2 = Volley.newRequestQueue(getApplicationContext());

        linkView(queue2);
    }

    private void linkView(final RequestQueue queue) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        diceRollFragment = (DiceRollFragment) fm.findFragmentById(R.id.rollToMoveFragment);
        diceRollFragment.setCallbackListener(this);
        diceRollFragment.getView().setVisibility(View.INVISIBLE);


        pollTurnInfo(queue);
    }


    private void pollTurnInfo(final RequestQueue queue2) {

        String url = "https://cs446-server.ue.r.appspot.com/turn";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                        try {
                            if (response.getInt("curr_turn") == id && response.getInt("last_updated_by") != id) {
                                System.out.println("id 1 turn");
                                diceRollFragment.getView().setVisibility(View.VISIBLE);
                                textView.setText("Your turn to roll!");
                                // update dice roll on server
                            }
                        else {
                                diceRollFragment.getView().setVisibility(View.INVISIBLE);
                                System.out.println("polling from dice activity");
                                textView.setText("Waiting for others to roll...");
                                pollTurnInfo(queue2);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue2.add(jsonObjectRequest);

    }

    //FragmentCallbackListener diceRolled
    @Override
    public void onCallback() {
        int diceRollResult = diceRollFragment.getDiceRollResult();
        System.out.println("dice roll: " + diceRollResult);
        diceRollFragment.getView().setVisibility(View.GONE);

        sendDiceRollToServer(diceRollResult);
    }

    public void sendDiceRollToServer(int rollValue) {
        System.out.println("send dice roll to server");

        String url = "https://cs446-server.ue.r.appspot.com/turn";

        JSONObject postData = new JSONObject();

        try {
            postData.put("Id", id);
            postData.put("RollValue", rollValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        System.out.println("sent dice roll to server");
                        pollTurnInfo(queue2);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue2.add(jsonObjectRequest);
    }

}
