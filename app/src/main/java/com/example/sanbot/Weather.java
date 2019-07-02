package com.example.sanbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Weather extends AppCompatActivity {

    public JSONArray WeatherArrayJson;
    //RequestQueue til volley
    public RequestQueue mQueue;
    //JsonObjectRequest til Volley
    public JsonObjectRequest EventRequest;
    //Api Url
    public String ApiUrl;

    ArrayList<JSONObject> WeatherListDary1 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary2 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary3 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary4 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary5 = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mQueue = Volley.newRequestQueue(this);
        ApiUrl = "http://api.openweathermap.org/data/2.5/forecast?id=2618425&appid=f5297e2b15ba441c138473e483bbfb29&units=metric";

        jsonParse();
    }

    private void jsonParse(){
        EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            WeatherArrayJson = response.getJSONArray("list");
                            String dt_txt = WeatherArrayJson.getJSONObject(0).getString("dt_txt").substring(0,10) + "";

                            int a = 0;

                            for(int i = 0; i < WeatherArrayJson.length(); i++){
                                JSONObject WeatherObjectJson = WeatherArrayJson.getJSONObject(i);
                                String dt_txtGet = WeatherObjectJson.getString("dt_txt").substring(0,10) + "";

                                switch (a){
                                    case 0:
                                        if (dt_txt.equals(dt_txtGet)){
                                            WeatherListDary1.add(WeatherObjectJson);
                                        }else{
                                            dt_txt = dt_txtGet;
                                            a++;
                                        }
                                        break;

                                    case 1:
                                        if (dt_txt.equals(dt_txtGet)){
                                            WeatherListDary2.add(WeatherObjectJson);
                                        }else{
                                            dt_txt = dt_txtGet;
                                            a++;
                                        }
                                        break;

                                    case 2:
                                        if (dt_txt.equals(dt_txtGet)){
                                            WeatherListDary3.add(WeatherObjectJson);
                                        }else{
                                            dt_txt = dt_txtGet;
                                            a++;
                                        }
                                        break;

                                    case 3:
                                        if (dt_txt.equals(dt_txtGet)){
                                            WeatherListDary4.add(WeatherObjectJson);
                                        }else{
                                            dt_txt = dt_txtGet;
                                            a++;
                                        }
                                        break;

                                    case 4:
                                        if (dt_txt.equals(dt_txtGet)){
                                            WeatherListDary5.add(WeatherObjectJson);
                                        }else{
                                            dt_txt = dt_txtGet;
                                            a++;
                                        }
                                        break;
                                }
                            }

                            Log.d("Listss", "WeatherListDary1: " + WeatherListDary1);
                            Log.d("Listss", "WeatherListDary2: " + WeatherListDary2);
                            Log.d("Listss", "WeatherListDary3: " + WeatherListDary3);
                            Log.d("Listss", "WeatherListDary4: " + WeatherListDary4);
                            Log.d("Listss", "WeatherListDary5: " + WeatherListDary5);

                            TextView DayTw = findViewById(R.id.DayTw1); ImageView WeatherImg = findViewById(R.id.WeatherImg1); TextView TempTw = findViewById(R.id.TempTw1); ListView WeatherList = findViewById(R.id.WeatherList1);
                            InsertInput(0, DayTw, WeatherImg, TempTw, WeatherList, WeatherListDary1);

                            DayTw = findViewById(R.id.DayTw2); WeatherImg = findViewById(R.id.WeatherImg2); TempTw = findViewById(R.id.TempTw2); WeatherList = findViewById(R.id.WeatherList2);
                            InsertInput(1, DayTw, WeatherImg, TempTw, WeatherList, WeatherListDary2);

                            DayTw = findViewById(R.id.DayTw3); WeatherImg = findViewById(R.id.WeatherImg3); TempTw = findViewById(R.id.TempTw3); WeatherList = findViewById(R.id.WeatherList3);
                            InsertInput(2, DayTw, WeatherImg, TempTw, WeatherList, WeatherListDary3);

                            DayTw = findViewById(R.id.DayTw4); WeatherImg = findViewById(R.id.WeatherImg4); TempTw = findViewById(R.id.TempTw4); WeatherList = findViewById(R.id.WeatherList4);
                            InsertInput(3, DayTw, WeatherImg, TempTw, WeatherList, WeatherListDary4);

                            DayTw = findViewById(R.id.DayTw5); WeatherImg = findViewById(R.id.WeatherImg5); TempTw = findViewById(R.id.TempTw5); WeatherList = findViewById(R.id.WeatherList5);
                            InsertInput(4, DayTw, WeatherImg, TempTw, WeatherList, WeatherListDary5);

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

    public int size = 0;

    public void InsertInput(int DayAddition, TextView DayTV, ImageView ImgV, TextView TempTV, ListView TempList, ArrayList<JSONObject> ArrayList) throws JSONException {

        String DateDate = ArrayList.get(0).getString("dt_txt").substring(0,10);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int DayOfweek = c.get(Calendar.DAY_OF_WEEK) + DayAddition;
        String Day = "";

            switch (DayOfweek){

                case 1:
                    Day = "Søndag";
                    break;

                case 2:
                    Day = "Mandag";
                    break;

                case 3:
                    Day = "Tirsdag";
                    break;

                case 4:
                    Day = "Onsdag";
                    break;

                case 5:
                    Day = "Torsdag";
                    break;

                case 6:
                    Day = "Fredag";
                    break;

                case 7:
                    Day = "Lørdag";
                    break;
            }

        DayTV.setText(Day);


        if (DateDate.equals(dateFormat.format(date))){
            TempTV.setText(ArrayList.get(0).getJSONObject("main").getString("temp").substring(0,2) + "\u00B0");

            int resID = getResources().getIdentifier("img"+ArrayList.get(0).getJSONArray("weather").getJSONObject(0).getString("icon") , "drawable", getPackageName());
            ImgV.setImageResource(resID);
        }else{
            TempTV.setText(ArrayList.get(4).getJSONObject("main").getString("temp").substring(0,2) + "\u00B0");

            int resID = getResources().getIdentifier("img"+ArrayList.get(4).getJSONArray("weather").getJSONObject(0).getString("icon") , "drawable", getPackageName());
            ImgV.setImageResource(resID);
        }

        Customadpater customadpater = new Customadpater(ArrayList);

        TempList.setAdapter(customadpater);
    }

    class Customadpater extends BaseAdapter {

        ArrayList<JSONObject> ArraylistAdapter = null;

        public Customadpater(ArrayList<JSONObject> Arraylist){
            ArraylistAdapter = Arraylist;
        }

        @Override
        public int getCount() {
            return ArraylistAdapter.size();
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
            convertView = getLayoutInflater().inflate(R.layout.weather_listitem, null);
            TextView Timeitem = convertView.findViewById(R.id.Timeitem);
            TextView Tempitem = convertView.findViewById(R.id.Tempitem);
            ImageView WeatherImageItem = convertView.findViewById(R.id.WeatherImageItem);

            try {
                Timeitem.setText(ArraylistAdapter.get(position).getString("dt_txt").substring(11));
                Tempitem.setText(ArraylistAdapter.get(position).getJSONObject("main").getString("temp"));

                int resID = getResources().getIdentifier("img"+ArraylistAdapter.get(position).getJSONArray("weather").getJSONObject(0).getString("icon") , "drawable", getPackageName());
                WeatherImageItem.setImageResource(resID);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Listss", "getView: Error");
            }

            return convertView;
        }
    }
}
