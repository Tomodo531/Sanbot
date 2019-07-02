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

    //RequestQueue til volley
    public RequestQueue mQueue;
    //JsonObjectRequest til Volley
    public JsonObjectRequest EventRequest;
    //Api Url
    public String ApiUrl;

    //Laver en List til hver dag hvor i vejret for hver tredje time vil blive added
    ArrayList<JSONObject> WeatherListDary1 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary2 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary3 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary4 = new ArrayList<JSONObject>();
    ArrayList<JSONObject> WeatherListDary5 = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //Laver min en VOlley requestqueue
        mQueue = Volley.newRequestQueue(this);
        //Url'en til Openweathermao key = f5297e2b15ba441c138473e483bbfb29
        ApiUrl = "http://api.openweathermap.org/data/2.5/forecast?id=2618425&appid=f5297e2b15ba441c138473e483bbfb29&units=metric";

        //Køre JsonParse som indeholder vores volley get function
        jsonParse();
    }

    //Dette funktion bruges til at hente data fra vores vejr API. Det er også her hvor vi putter data'en fra hver enkelte dag ind i veres seperate lists.
    private void jsonParse(){

        //Opretter vores Get Request.
        EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Henter jsonarray'et som indeholder vejr info 5 dage frem samt ændringer i vejret hver tredje time.
                            JSONArray WeatherArrayJson = response.getJSONArray("list");
                            //Henter datoen for første dag og gemmer den til senere brug.
                            String dt_txt = WeatherArrayJson.getJSONObject(0).getString("dt_txt").substring(0,10) + "";

                            //Int'en a bruges i nederstående switchcase til skifte imellem de forskellige lists som vi oprettede i starten som indeholder alt vejr data for hver enkelte dag
                            int a = 0;

                            //Dette for loop checker alt data i vores WeatherArrayJson og sorterer det ind i de forskellioge lists
                            for(int i = 0; i < WeatherArrayJson.length(); i++){

                                //Henter Json object on ligger i index i
                                JSONObject WeatherObjectJson = WeatherArrayJson.getJSONObject(i);
                                //Her henter vi datoen for dataen i overstående object. vi bruger en substring til at fjerne den sidste del af datoen som er tidspunktet fx. 2019/07/02 00:00:00 -> 2019/07/02.
                                //Grunden til vi henter denne dato er så vi kan samligne den med dt_txt som er datoen for første dag. Vi gør dette så vi ved hvilken list den skal i.
                                String dt_txtGet = WeatherObjectJson.getString("dt_txt").substring(0,10) + "";

                                //Switchcase som udfra værdien af a ved hvilken list data'en skal puttes i.
                                switch (a){
                                    case 0:
                                        //Her sammenligner vi dt_txt og dt_txtGet for at se om den hører hjemme i nederstående list. hvis ikke ligges der 1 til a som får switchcasen til at gå videre til næste case.
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

                            //Her kalder vi vores funktion ved navn InsertInput som putter vores hentede data ind i sperate cardviews for hver dag (5 dage).
                            //I funktionen har vi 6 parametr hvor af DayTw, WeatherImg, TempTw, WeatherList er id'erne på de elementerne hvori data'en skal udskrives. DayAdditon er hvormange dage der skal ligges til
                            //fx. hvis det er tirsdag idag og der står 1 i DayAdditon ligger den en dag til tirsdag så det bliver onsdag. Det sidste paramenter er vores list med vores data.


                            //DayTw = TextView. Indeholder Ugedag. WeatherImg = ImageView. Indeholder Icon for vejret. TempTw = TextView. Indeholder temperaturen. WeatherList = ListView. Indeholder Vejr data for hver tredje time.
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

    //InsertInput funktionen er funktionen som inserter vores data ind i vores elementer.
    public void InsertInput(int DayAddition, TextView DayTV, ImageView ImgV, TextView TempTV, ListView TempList, ArrayList<JSONObject> ArrayList) throws JSONException {

        //Henter dato for dagen i vores list.
        String DateDate = ArrayList.get(0).getString("dt_txt").substring(0,10);

        //Laver en SimpleDateFormat samt en ny Date variable som indeholder dags dato.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        //Her bruger vi java.util.Date til at finde ud af hvilken ugedag vi har.

        //Opretter en Calender variable.
        Calendar c = Calendar.getInstance();
        //Sætter Calender til dags dato.
        c.setTime(date);
        //Henter ugedag og ligger vores DayAddition til.
        int DayOfweek = c.get(Calendar.DAY_OF_WEEK) + DayAddition;

        //Opretter String variablen day som vil indeholde vores ugedag i tesktform istedet for tal
        String Day = "";

            //Switchcase som finder frem til vore ugedag i Tekst form
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

        //Udskirver ugedagen i TextView'en DayTV
        DayTV.setText(Day);

        //I disse to if else statements finder vi ud af om listen vi har fat i er listen for dags dato eller om det er en anden dag. gurnden til vi gør dette er fordi,
        //hvis det er dags dato vil vi gerne udskrive hvordan vejret er lige nu og hvis det ikke er dags dato vil vi gerne udsrkive hvordan vejret er midt på dagen i vores tilfælde er det kl 15:00.

        //Tjekker om det er dags dato
        if (DateDate.equals(dateFormat.format(date))){

            //udskriver nuværende temperatur. "\u00B0" = °
            TempTV.setText(ArrayList.get(0).getJSONObject("main").getString("temp").substring(0,2) + "\u00B0");

            //Her henter vi vejr iconet som representere vejret og tager det fra string til en int
            int resID = getResources().getIdentifier("img"+ArrayList.get(0).getJSONArray("weather").getJSONObject(0).getString("icon") , "drawable", getPackageName());
            //Udsrkiver billedet.
            ImgV.setImageResource(resID);
        }else{
            //Udsrkiver temperaturen midt på dagen 15:00
            TempTV.setText(ArrayList.get(4).getJSONObject("main").getString("temp").substring(0,2) + "\u00B0");

            //Her henter vi vejr iconet som representere vejret og tager det fra string til en int
            int resID = getResources().getIdentifier("img"+ArrayList.get(4).getJSONArray("weather").getJSONObject(0).getString("icon") , "drawable", getPackageName());
            //Udsrkiver billedet.
            ImgV.setImageResource(resID);
        }

        //Her Udskriver vi vores resterende data altså vejret for hver tredje time. Vi gør dette ved at udsrkive det i en ListView ved hjælp af en Customadpater.

        //Kalder vores Customadpater
        Customadpater customadpater = new Customadpater(ArrayList);

        //Udskriver data'en i vores ListView. TempList er ListViewens id som var en af de 6 parameter fra tidligere.
        TempList.setAdapter(customadpater);
    }

    class Customadpater extends BaseAdapter {

        //Opretter en ArrayList hvor vores data vil blive gemt
        ArrayList<JSONObject> ArraylistAdapter = null;

        //contructor som henter data'en til ArrayList'en
        public Customadpater(ArrayList<JSONObject> Arraylist){
            ArraylistAdapter = Arraylist;
        }

        //Finder længden af vores liste for at finde ud af hvor mange gange vi skal køre funktionen.
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

        //Her udskriver vi vores data i vores elementer
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Opretter vores View som er det layout som vi udsrkiver vores data i.
            convertView = getLayoutInflater().inflate(R.layout.weather_listitem, null);

            //Opretter alle vores elementer
            TextView Timeitem = convertView.findViewById(R.id.Timeitem);
            TextView Tempitem = convertView.findViewById(R.id.Tempitem);
            ImageView WeatherImageItem = convertView.findViewById(R.id.WeatherImageItem);

            try {
                //Henter og udsrkiver tidspunktet
                Timeitem.setText(ArraylistAdapter.get(position).getString("dt_txt").substring(11));
                //Henter og udsrkiver temperaturen
                Tempitem.setText(ArraylistAdapter.get(position).getJSONObject("main").getString("temp"));

                //Henter Icon navn og laver det om til et id
                int resID = getResources().getIdentifier("img"+ArraylistAdapter.get(position).getJSONArray("weather").getJSONObject(0).getString("icon") , "drawable", getPackageName());
                //Udskriver billedet
                WeatherImageItem.setImageResource(resID);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Listss", "getView: Error");
            }

            //Returner vores view
            return convertView;
        }
    }
}
