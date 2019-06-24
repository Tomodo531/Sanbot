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
            //Trying to open a connection to the url (the rss url)
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
            //The loading message, before the news shows up
            progressDialog.setMessage("Loader Nyheder...Vent Venligst...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                //Getting news data from the news' rss website
                URL url = new URL("http://feeds.tv2.dk/nyhederne_seneste/rss");

                //create a new pool parser factory object.
                //Basically help us to retrieve the data from the xml document
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by the factory
                //will provide support for XML namespaces or not
                factory.setNamespaceAware(false);

                //Creating a new pool parser
                XmlPullParser sa = factory.newPullParser();

                //new input stream, which makes a new connection
                //and we can start reading using the XML pool parser object
                //start reading from the URL XML document that is utf-8 encoded
                sa.setInput(getInputStream(url), "UTF_8");

                //Boolean to tell when inside the item tag in the rss url
                boolean insideItem = false;

                //When start reading from it, when call getEventType, it returns the type of the current event
                int eventType = sa.getEventType();

                //Keep showing the newsfeed, until reaching the end of the document
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if it's a start of specific tags
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //if item tag is "item",
                        //We will be inside the item tag
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
                            //Add new item tag next to the other, if already inside another item tag

                            if (insideItem)
                            {

                                links.add(sa.nextText());
                            }
                        }
                    }
                    //If reaching end and still inside item tag, we will stop reading inside the item tag
                    else if (eventType == XmlPullParser.END_TAG && sa.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    //going tyo the next item tag/news
                    eventType = sa.next();
                }


            }
            //something wrong with the URL we typed?
            catch (MalformedURLException e)
            {
                exception = e;
            }
            //Try to extract a data
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            //Input / Output exception
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
