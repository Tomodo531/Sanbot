package com.example.sanbot;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Calendar extends AppCompatActivity {

    private SimpleDateFormat DataFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final CompactCalendarView compactCalendar = findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        TextView CalenderInfo = findViewById(R.id.CalenderInfo);
        CalenderInfo.setText(DataFormatMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));

        mQueue = Volley.newRequestQueue(this);

        String ApiUrl = "http://myresume.dk/PHP/GetPopUpContentJSON.php";

        JsonObjectRequest EventRequest = new JsonObjectRequest(Request.Method.GET, ApiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Evnets");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject Event = jsonArray.getJSONObject(i);

                                String dateUnFormat = Event.getString("Epoch");
                                String EventTitle = Event.getString("EventTitle");
                                String EventInfo = Event.getString("EventInfo");

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                Date date = df.parse(dateUnFormat);
                                long epoch = date.getTime();

                                compactCalendar.addEvent(new Event(Color.WHITE, epoch, date + "@@" + EventTitle + "@@" + EventInfo));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
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

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Object[] EventArray = events.toArray();
                Log.d("EventData", "Day was clicked: " + dateClicked + " with events " + events);


                Intent mDisplayEvnts = new Intent(Calendar.this, DisplayEvents.class);
                String listSerializedToJson = new Gson().toJson(EventArray);
                mDisplayEvnts.putExtra("LIST_OF_OBJECTS", listSerializedToJson);
                startActivity(mDisplayEvnts);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                TextView CalenderInfo = findViewById(R.id.CalenderInfo);
                CalenderInfo.setText(DataFormatMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));
            }
        });
    }
}
