package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.watopoly.R;
import com.example.watopoly.adapter.RollToGoFirstAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DisplayPlayerOrderActivity extends AppCompatActivity {
    private ArrayList<Integer> iconsList = new ArrayList<>();
    private ArrayList<String> coloursList = new ArrayList<>();
    private ArrayList<String> namesList = new ArrayList<>();
    private ArrayList<Integer> rollsList = new ArrayList<>();
    private ArrayList<Integer> idsList = new ArrayList<>();

    private ArrayList<Integer> tempIcons = new ArrayList<>();
    private ArrayList<String> tempColours = new ArrayList<>();
    private ArrayList<String> tempNames = new ArrayList<>();
    private ArrayList<Integer> tempRolls = new ArrayList<>();
    private ArrayList<Integer> tempIds = new ArrayList<>();


    private RollToGoFirstAdaptor shapeAdapter;
    private RecyclerView shapeRecyclerView;

    private Context c = this;
    int id = -1;
    int icon = -1;
    String colour = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_player_order);

        final RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        icon = intent.getIntExtra("icon", -1);
        colour = intent.getStringExtra("colour");
        name = intent.getStringExtra("name");


        setupOrdering(queue);
        Button passToFirstPlayerBtn = findViewById(R.id.passToFirstPlayerBtn);
        passToFirstPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 1) {
                    Intent intent = new Intent(c, MainGameViewActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("icons", iconsList);
                    intent.putExtra("colours", coloursList);
                    intent.putExtra("names", namesList);
                    intent.putExtra("ids", idsList);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(c, GameDiceRollActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("icon", icon);
                    intent.putExtra("colour", colour);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }

    private void setupOrdering(RequestQueue queue) {

        String url = "https://cs446-server.ue.r.appspot.com/get-arrays";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        System.out.println(response.names());
                        try {
                            JSONArray names = response.getJSONArray((String) response.names().get(0)).getJSONArray(0);
                            JSONArray icons = response.getJSONArray((String) response.names().get(0)).getJSONArray(1);
                            JSONArray colours = response.getJSONArray((String) response.names().get(0)).getJSONArray(2);
                            JSONArray rolls = response.getJSONArray((String) response.names().get(0)).getJSONArray(3);
                            JSONArray ids = response.getJSONArray((String) response.names().get(0)).getJSONArray(4);

                            for (int i = 0; i < names.length(); i++) {
                                tempIcons.add(icons.getInt(i));
                                tempNames.add(names.getString(i));
                                tempColours.add(colours.getString(i));
                                tempRolls.add(rolls.getInt(i));
                                tempIds.add(ids.getInt(i));
                            }
                            linkView();



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
        queue.add(jsonObjectRequest);

    }

    private void linkView(){

        ArrayList<Pair<Integer, Integer>> ordering = new ArrayList<>();
        for (int x = 0; x < tempRolls.size(); x++) { ordering.add(new Pair<Integer, Integer>(tempRolls.get(x), x)); }
        Collections.sort(ordering, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o1.first > o2.first ? -1 : 1;
            }
        });

        for (Pair<Integer, Integer> pair: ordering) {
            coloursList.add(tempColours.get(pair.second));
            iconsList.add(tempIcons.get(pair.second));
            namesList.add(tempNames.get(pair.second));
            rollsList.add(tempRolls.get(pair.second));
            idsList.add(tempIds.get(pair.second));
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
        shapeRecyclerView.setLayoutManager(layoutManager1);
        shapeAdapter = new RollToGoFirstAdaptor(iconsList, coloursList, rollsList);
        shapeRecyclerView.setAdapter(shapeAdapter);
    }
}