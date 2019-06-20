package com.example.sanbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowInfoMadplan extends AppCompatActivity {

    //RequestQueue til volley
    public RequestQueue mQueue;
    public String ApiUrl;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_madplan);

        Intent in = getIntent();
        ApiUrl = in.getStringExtra("com.example.sanbot.ApiUrl");
        position = in.getIntExtra("com.example.sanbot.MadplanInfo", -1);

        Log.d("InfoMad", "ApiUrl: " + ApiUrl);
        Log.d("InfoMad", "position: " + position);

        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse(){
        JsonObjectRequest EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Madplan");
                            JSONObject InfoObject = jsonArray.getJSONObject(position);

                            Log.d("InfoMad", "InfoObject: ");

                            TextView TextTitel = findViewById(R.id.textView7);
                            TextView InfoDay = findViewById(R.id.textView8);
                            TextView InfoText = findViewById(R.id.InfoText);

                            TextTitel.setText(InfoObject.getString("Title"));
                            InfoDay.setText(InfoObject.getString("Day").substring(1));
                            InfoText.setText(InfoObject.getString("Infomation"));

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d("InfoMad", "Volley: Error");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("InfoMad", "ApiUrl: " + ApiUrl);
                        Log.d("InfoMad", "Volley: Error " + error);
                    }
                });

        mQueue.add(EventRequest);
    }

}
