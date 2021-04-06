package com.example.watopoly.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
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

public class WaitForRoomActivity extends AppCompatActivity {

    TextView numPlayers;
    final Context c = this;
    int id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_room);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        }

        numPlayers =  findViewById(R.id.numPlayers);
        final RequestQueue queue = Volley.newRequestQueue(this);
        recurse(queue);

        System.out.println("hooray");
    }

    private void recurse(final RequestQueue queue)
    {
        String url = "https://cs446-server.ue.r.appspot.com/room";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int maxPlayers = response.getInt("max_players");
                            int currPlayers = response.getJSONArray("players").length();
                            System.out.println(maxPlayers + " " + currPlayers);
                            if (maxPlayers <= currPlayers) {
                                String ok = currPlayers + "/" + maxPlayers + " joined";
                                numPlayers.setText(ok);
                                System.out.println(response.toString());
                                System.out.println("hello");
                                Intent intent = new Intent(c, EnterPlayerInfoActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);

                            }
                            else {
                                String ok = currPlayers + "/" + maxPlayers + " joined";
                                numPlayers.setText(ok);
                                recurse(queue);
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
        queue.add(jsonObjectRequest);
    }
}
