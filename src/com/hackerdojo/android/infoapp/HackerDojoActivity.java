package com.hackerdojo.android.infoapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HackerDojoActivity extends ListActivity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Events");
        
        HttpClient client = new DefaultHttpClient();
		StringBuffer sb = new StringBuffer();
        try {
        	Log.i("HACKER_DOJO", "FETCHING EVENTS");
			HttpResponse response = client.execute(new HttpGet("http://events.hackerdojo.com/events.json"));
			HttpEntity entity = response.getEntity();
			char[] buf = new char[1024];
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			int c=0;
			while(c != -1) {
				c = reader.read(buf);
				if(c > 0) {
					sb.append(buf, 0, c);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ArrayList<String> events = new ArrayList<String>();
        try {
        	JSONArray json = new JSONArray(sb.toString());
        	for(int i=0; i<json.length(); i++) {
        		JSONObject jsonObject = json.getJSONObject(i);
        		String nextEvent = "no time set";
        		if(jsonObject.has("start_time")) {
        			nextEvent = jsonObject.getString("start_time");
        		}
        		if(jsonObject.has("name")) {
        			events.add(nextEvent + " - " + jsonObject.getString("name"));
        		}
        	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String[] s = new String[events.size()];
        for(int i=0; i<events.size(); i++) {
        	s[i] = events.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.entry, s);
        setListAdapter(adapter);
    }

	@Override
	public void onClick(View v) {
		
	}
}