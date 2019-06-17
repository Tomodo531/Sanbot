package com.example.sanbot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        RelativeLayout.LayoutParams p=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        lvRss = (ListView) findViewById(R.id.lvRss);

        p.setMargins(30, 30, 30, 30);

        lvRss.setLayoutParams(p);

        titles = new ArrayList<String>();
        links = new ArrayList<String>();

        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        new ProcessInBackground().execute();
    }

    public InputStream getInputStream(URL url)
    {
        try
        {

            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog progressDialog = new ProgressDialog(NewsActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loader Nyheder...Vent Venligst...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                //Getting news data from the website via rss
                URL url = new URL("http://feeds.tv2.dk/nyhederne_seneste/rss");


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();


                factory.setNamespaceAware(false);


                XmlPullParser sa = factory.newPullParser();


                sa.setInput(getInputStream(url), "UTF_8");


                boolean insideItem = false;


                int eventType = sa.getEventType();

                //Keep showing the newsfeed, until reaching end
                while (eventType != XmlPullParser.END_DOCUMENT)
                {

                    if (eventType == XmlPullParser.START_TAG)
                    {

                        if (sa.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;

                        }

                        else if (sa.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {

                                titles.add(sa.nextText());
                            }
                        }

                        else if (sa.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {

                                links.add(sa.nextText());
                            }
                        }
                    }

                    else if (eventType == XmlPullParser.END_TAG && sa.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = sa.next();
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, titles);

            lvRss.setAdapter(adapter);

            progressDialog.dismiss();
        }
    }
}