package com.example.sanbot;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    public JSONArray List;

    public ItemAdapter(Context c, JSONArray l){
        List = l;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return List.length();
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

        View v = mInflater.inflate(R.layout.madplanitem_container, null);
        TextView textView = v.findViewById(R.id.textView);
        TextView textView2 = v.findViewById(R.id.textView2);

        textView.setText("Test Iitel");
        textView2.setText("Test Day");

        Log.d("Madplan", "getView: Run");

        return v;
    }
}
