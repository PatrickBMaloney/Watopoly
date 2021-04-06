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

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button button =  findViewById(R.id.button);
        final NumberPicker numberPicker =  findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(4);
        final EditText editText =  findViewById(R.id.editText);
        final RequestQueue queue = Volley.newRequestQueue(this);

        final Context c = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cs446-server.ue.r.appspot.com/room";

                System.out.println("hello world");

                JSONObject hostData = new JSONObject();
                JSONObject postData = new JSONObject();

                try {
                    hostData.put("Name", "12345");
                    hostData.put("Icon", 12345);
                    hostData.put("InitialRoll", 12345);

                    postData.put("Name", editText.getText());
                    postData.put("MaxPlayers", numberPicker.getValue());
                    postData.put("Host", hostData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                                Intent intent = new Intent(c, WaitForRoomActivity.class);
                                intent.putExtra("id", 1);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });
                queue.add(jsonObjectRequest);

            }
        });



    }

}
