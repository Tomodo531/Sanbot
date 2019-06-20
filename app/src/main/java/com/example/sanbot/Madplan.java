package com.example.sanbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class Madplan extends AppCompatActivity {

    //RequestQueue til volley
    public RequestQueue mQueue;
    //JSONArray som indeholder data fra Volley
    public JSONArray jsonArray;
    //JsonObjectRequest til Volley
    public JsonObjectRequest EventRequest;
    //Size opbevare størrelsen på jsonArray
    public int size;
    //Api Url
    public String ApiUrl;
    //Weeknum indikere antal uger der skal gå frem og tilbage
    public int WeekNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madplan);


        mQueue = Volley.newRequestQueue(this);
        ApiUrl = "http://myresume.dk/SanbotWebsite/php/API/MadplanAPI.php?week=" + WeekNum;

        jsonParse();

        ImageButton Forwards = findViewById(R.id.Forwards);
        ImageButton Backwards = findViewById(R.id.Backwards);

        Forwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekNum++;
                ApiUrl = "http://myresume.dk/SanbotWebsite/php/API/MadplanAPI.php?week=" + WeekNum;

                jsonParse();
            }
        });

        Backwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekNum--;
                ApiUrl = "http://myresume.dk/SanbotWebsite/php/API/MadplanAPI.php?week=" + WeekNum;

                jsonParse();
            }
        });

        ListView MadplanList = findViewById(R.id.MadplanList);

        MadplanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ShowMadplan = new Intent(getApplicationContext(), ShowInfoMadplan.class);
                ShowMadplan.putExtra("com.example.sanbot.MadplanInfo", position);
                ShowMadplan.putExtra("com.example.sanbot.ApiUrl", ApiUrl);
                startActivity(ShowMadplan);
            }
        });

    }

    private void jsonParse(){
        EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = null;
                            jsonArray = response.getJSONArray("Madplan");
                            size = jsonArray.length();

                            Log.d("Madplan", "ApiUrl: " + ApiUrl);
                            Log.d("Madplan", "Volley: Success " + jsonArray);

                            TextView UgeNr = findViewById(R.id.UgeNr);
                            UgeNr.setText("Uge Nr. " + response.getString("week") + " " + response.getString("year"));

                            if (size == 0){
                                TextView NoItems = findViewById(R.id.NoItems);
                                NoItems.setText("Ingen madplan");
                            }

                            Customadpater customadpater = new Customadpater();

                            ListView MadplanList = findViewById(R.id.MadplanList);
                            MadplanList.setAdapter(customadpater);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Madplan", "ApiUrl: " + ApiUrl);
                        Log.d("Madplan", "Volley: Error " + error);
                    }
                });

        mQueue.add(EventRequest);
    }

    class Customadpater extends BaseAdapter{

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.madplanitem_container, null);
            TextView textView = convertView.findViewById(R.id.textView);
            TextView textView2 = convertView.findViewById(R.id.textView2);

            try {
                JSONObject MadplanObject = jsonArray.getJSONObject(position);

                textView.setText(MadplanObject.getString("Title"));
                textView2.setText(MadplanObject.getString("Day").substring(1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

}
