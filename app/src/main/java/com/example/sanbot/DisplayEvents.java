package com.example.sanbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayEvents extends AppCompatActivity {

    public String GetDate = null;
    public String dateClicked = null;
    public JSONArray EventsArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);

        GetDate = getIntent().getExtras().getString("LIST_OF_OBJECTS");
        dateClicked = getIntent().getExtras().getString("dateClicked");
        ListView EventList = findViewById(R.id.EventList);

        JSONObject root = null;
        try {
            root = new JSONObject("{Events:" + GetDate + "}");
            EventsArray = root.getJSONArray("Events");

            TextView DateTextView = findViewById(R.id.DateTextView);
            DateTextView.setText(dateClicked.substring(0, 11));

            if (EventsArray.length() == 0){
                TextView NoEventsTextView = findViewById(R.id.NoEventsTextView);
                NoEventsTextView.setText("No Events");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Customadapter customadapter = new Customadapter();

        EventList.setAdapter(customadapter);
    }

    private class Customadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return EventsArray.length();
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
            convertView = getLayoutInflater().inflate(R.layout.event_container, null);
            TextView EventTitle = convertView.findViewById(R.id.EventTitle);
            TextView EventDate = convertView.findViewById(R.id.EventDate);
            TextView EventInfo = convertView.findViewById(R.id.EventInfo);

            try {
                JSONObject EvenyObj = EventsArray.getJSONObject(position);
                String Data = EvenyObj.getString("data");

                String[] DataSplit = Data.split("@@");

                EventTitle.setText(DataSplit[1]);
                EventDate.setText(DataSplit[0]);
                EventInfo.setText(DataSplit[2]);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSONObjectError", "getView: ");
            }

            return convertView;
        }
    }
}
