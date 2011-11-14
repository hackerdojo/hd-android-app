package com.hackerdojo.android.infoapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StaffActivity extends HackerDojoActivity implements OnClickListener {
    private static final String HACKER_DOJO = "HACKER_DOJO";
    private static List<Person> currentStaff;
    private static Calendar lastUpdated = Calendar.getInstance();
	private static List<String> names;
	private static List<String> timeHere;
	private static List<String> images;
    static {
    	lastUpdated.setTime(new Date(0));
    }

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
	private List<Person> getStaff() {
        HttpClient client = new DefaultHttpClient();
		StringBuffer sb = new StringBuffer();
        try {
        	Log.i(HACKER_DOJO, "FETCHING EVENTS");
			HttpResponse response = client.execute(new HttpGet("http://hackerdojo-signin.appspot.com/staffjson"));
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
			Log.e(HACKER_DOJO, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(HACKER_DOJO, e.getMessage(), e);
		}
        
        ArrayList<Person> currentStaffBuilder = new ArrayList<Person>();
        try {
        	JSONArray json = new JSONArray(sb.toString());
        	for(int i=0; i<json.length(); i++) {
        		Person person = new Person();
        		JSONObject jsonObject = json.getJSONObject(i);
        		if(jsonObject.has("name") && jsonObject.has("created") && jsonObject.has("image_url") && jsonObject.has("name")) {
    				SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    				
        			String name = jsonObject.getString("name");
        			Log.e(HACKER_DOJO, "created: " + jsonObject.getString("created"));
    				Date created = format.parse(jsonObject.getString("created")); // hardcode date for parsing
        			String imageUrl = jsonObject.getString("image_url");
        			
    				person.setName(name);
        			person.setCreated(created);
        			person.setImageUrl(imageUrl);
        			currentStaffBuilder.add(person);
        		}
        	}
		} catch (JSONException e) {
			Log.e(HACKER_DOJO, e.getMessage(), e);
		} catch (ParseException e) {
			Log.e(HACKER_DOJO, e.getMessage(), e);
		}
        Collections.sort(currentStaffBuilder);
        return currentStaffBuilder;
    }
    
	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	protected void initializeView() {
		setHeader("Staff on Site");
    	if(Calendar.getInstance().getTimeInMillis() - lastUpdated.getTimeInMillis() > 600000 /* 10 minutes */) {
	        lastUpdated = Calendar.getInstance();
	        
	        currentStaff = getStaff();
	        
	        names = new ArrayList<String>();
	        timeHere = new ArrayList<String>();
	        images = new ArrayList<String>();
        	TextView emptyMessage = (TextView) findViewById(R.id.empty);
	        if(currentStaff.size() == 0) {
	        	emptyMessage.setText("Dojo is closed");
	        	emptyMessage.setVisibility(View.VISIBLE);
	        	lastUpdated.setTime(new Date(0));
	        } else {
	        	emptyMessage.setVisibility(View.GONE);
	        }
	        for(int i=0; i<currentStaff.size(); i++) {
	        	Person person = currentStaff.get(i);
	        	Calendar timeEntered = Calendar.getInstance();
	        	timeEntered.setTime(person.getCreated());
	        	
	        	long timeRadix = lastUpdated.getTimeInMillis() - timeEntered.getTimeInMillis();
	        	
	        	// normalize for timezone
	        	long offset = TimeZone.getDefault().getRawOffset();
	        	timeRadix = timeRadix - offset;
	        	
	        	timeRadix = timeRadix / 60;
	        	timeRadix = timeRadix / 1000;
	        	Log.e(HACKER_DOJO, "lastUpdated: " + lastUpdated);
	        	Log.e(HACKER_DOJO, "timeEntered: " + timeEntered);
	        	Log.e(HACKER_DOJO, "radix: " + timeRadix);
	        	
	        	// time is UTC
	        	
	        	long minutes = timeRadix % 60;
	        	timeRadix = timeRadix / 60;
	        	long hours = timeRadix % 24;
	        	timeRadix = timeRadix / 24;
	        	long days = timeRadix;
	        	
	        	StringBuffer time = new StringBuffer();
	        	if(days > 0) {
	        		time.append(days + " days ");
	        	}
	        	if(days > 0 || hours > 0) {
	        		time.append(hours + " hours ");
	        	}
	        	if(days > 0 || hours > 0 || minutes > 0) {
	        		time.append(minutes + " minutes");
	        	}
	        	names.add(person.getName());
	        	timeHere.add(time.toString());
	        	images.add("[image]");
	        }
    	}
        StaffArrayAdapter adapter2 = new StaffArrayAdapter(this, images, names, timeHere);
        setListAdapter(adapter2);
        
        Button staffButton = (Button)findViewById(R.id.staffButton);
        staffButton.setVisibility(View.GONE);
	}
}
