package com.example.sanbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public RequestQueue mQueue;
    public JSONArray jsonArray;
    public int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madplan);


        mQueue = Volley.newRequestQueue(this);

        String ApiUrl = "http://myresume.dk/SanbotWebsite/php/API/MadplanAPI.php?week=1&year=2019";

        JsonObjectRequest EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            jsonArray = response.getJSONArray("Madplan");
                            size = jsonArray.length();

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

            textView.setText("Test Iitel");
            textView2.setText("Test Day");

            Log.d("Madplan", "getView: Run");

            return convertView;
        }
    }

}
