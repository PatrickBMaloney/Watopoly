package com.example.watopoly.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.watopoly.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowRoomsActivity extends AppCompatActivity {

    final Context c = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rooms);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final RequestQueue queue = Volley.newRequestQueue(this);
        final TextView rn = findViewById(R.id.roomName);
        Button button = findViewById(R.id.joinRoom);
        final TextView playersJoined = findViewById(R.id.playersJoined);


        String url = "https://cs446-server.ue.r.appspot.com/room";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("got show rooms response");
                            rn.setText(response.getString("name"));
                            playersJoined.setText(response.getJSONArray("players").length() + "/" + response.getInt("max_players") + " joined");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                    }
                });
        queue.add(jsonObjectRequest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinRoom(queue);

            }
        });



    }

    private void joinRoom(RequestQueue requestQueue) {
        String url = "https://cs446-server.ue.r.appspot.com/player";

        JSONObject hostData = new JSONObject();
        try {
            hostData.put("Name", "joiner");
            hostData.put("Icon", -1);
            hostData.put("InitialRoll", 12);
            hostData.put("Colour", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, hostData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                        try {
                            int id = response.getInt("id");
                            Intent intent = new Intent(c, WaitForRoomActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);

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
        requestQueue.add(jsonObjectRequest);



    }

}
