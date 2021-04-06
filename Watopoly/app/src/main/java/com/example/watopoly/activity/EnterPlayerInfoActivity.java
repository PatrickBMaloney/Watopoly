package com.example.watopoly.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.watopoly.R;
import com.example.watopoly.adapter.PlayerInfoListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnterPlayerInfoActivity extends AppCompatActivity {
    private PlayerInfoListAdapter shapeAdapter;
    private PlayerInfoListAdapter colourAdapter;
    private RecyclerView shapeRecyclerView;
    private RecyclerView colourRecyclerView;
    private TextView titleTextView;
    private EditText nameEditText;
    private Button enterPlayerInfoButton;


    private int id = 1;
    RequestQueue queue;
    Context c;

    private ArrayList<Integer> pathSelected = new ArrayList<>();
    private ArrayList<String> colourSelected = new ArrayList<>();
    private  ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_player_info);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        queue = Volley.newRequestQueue(this);
         c = this;


        linkView();
        setupAdapter();
        enterPlayerInfo();
    }

    private void linkView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
        shapeRecyclerView.setLayoutManager(layoutManager1);
        colourRecyclerView = findViewById(R.id.ColourOptionRecyclerView);
        colourRecyclerView.setLayoutManager(layoutManager2);
        titleTextView = findViewById(R.id.enterPlayerInfoTitleTextView);
        nameEditText = findViewById(R.id.nameEditText);
        enterPlayerInfoButton = findViewById(R.id.enterPlayerInfoButton);
        enterPlayerInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEntering();
            }
        });
    }

    private void setupAdapter() {
        ArrayList<Integer> shapePath = new ArrayList<>();
        shapePath.add(R.drawable.anchor);
        shapePath.add(R.drawable.android);
        shapePath.add(R.drawable.film);
        shapePath.add(R.drawable.pet);
        shapePath.add(R.drawable.baseball);

        ArrayList<String> shapeColour = new ArrayList<>();
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeColour.add("#000000");
        shapeAdapter = new PlayerInfoListAdapter(shapePath, shapeColour);
        shapeRecyclerView.setAdapter(shapeAdapter);

        ArrayList<Integer> colourPath = new ArrayList<>();
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);
        colourPath.add(R.drawable.colour_icon);

        ArrayList<String> colours = new ArrayList<>();
        colours.add("#751A33");
        colours.add("#B34233");
        colours.add("#D28F33");
        colours.add("#D4B95E");
        colours.add("#4EA2A2");
        colours.add("#1A8693");
        colourAdapter = new PlayerInfoListAdapter(colourPath, colours);
        colourRecyclerView.setAdapter(colourAdapter);
    }

    private void enterPlayerInfo(){
       // dialog.show();

        titleTextView.setText("Player "+id+ " Enter Info");
        nameEditText.setText("");
        colourAdapter.setSelected(-1);
        shapeAdapter.setSelected(-1);
        colourAdapter.notifyDataSetChanged();
        shapeAdapter.notifyDataSetChanged();
    }


    private void finishEntering(){
        //TODO: show error msg
        if (shapeAdapter.getSelected() == -1 || colourAdapter.getSelected() == -1 || nameEditText.getEditableText().toString().equals("")) { return; }

        //save data
        pathSelected.add(shapeAdapter.getPaths().get(shapeAdapter.getSelected()));
        colourSelected.add(colourAdapter.getColours().get(colourAdapter.getSelected()));
        names.add(nameEditText.getText().toString());

        String url = "https://cs446-server.ue.r.appspot.com/modify-player";

        JSONObject hostData = new JSONObject();
        try {
            hostData.put("Name", nameEditText.getText().toString());
            hostData.put("Icon", shapeAdapter.getPaths().get(shapeAdapter.getSelected()));
            hostData.put("InitialRoll", -1);
            hostData.put("Colour", colourAdapter.getColours().get(colourAdapter.getSelected()));
            hostData.put("Id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, hostData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        Intent intent = new Intent(c, RollToGoFirstActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("colour", colourAdapter.getColours().get(colourAdapter.getSelected()));
                        intent.putExtra("icon", shapeAdapter.getPaths().get(shapeAdapter.getSelected()));
                        intent.putExtra("name", nameEditText.getText().toString());
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // TODO: Handle error

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("headerKey", "headerValue");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bodyKey", "bodyValue");
                return params;
            }
        };
        queue.add(jsonObjectRequest);

    }
}