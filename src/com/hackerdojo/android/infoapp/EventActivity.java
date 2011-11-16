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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EventActivity extends HackerDojoActivity implements OnClickListener {
    private static final String HACKER_DOJO = "HACKER_DOJO";
    private static List<Event> events;
    private static Calendar lastUpdated = Calendar.getInstance();
	private static List<String> startDates;
	private static List<String> endDates;
	private static List<String> titles;
    static {
    	lastUpdated.setTime(new Date(0));
    }
    
    
	private List<Event> getEvents() {
        HttpClient client = new DefaultHttpClient();
		StringBuffer sb = new StringBuffer();
        try {
        	Log.i(HACKER_DOJO, "FETCHING EVENTS");
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
			Log.e(HACKER_DOJO, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(HACKER_DOJO, e.getMessage(), e);
		}
        
        ArrayList<Event> events = new ArrayList<Event>();
        try {
        	JSONArray json = new JSONArray(sb.toString());
        	for(int i=0; i<json.length(); i++) {
        		Event event = new Event();
        		JSONObject jsonObject = json.getJSONObject(i);
        		if(jsonObject.has("status") && jsonObject.has("start_time") && jsonObject.has("end_time") && jsonObject.has("name") && jsonObject.has("member") && jsonObject.has("rooms")) {
        			if(jsonObject.getString("status").equals("approved")) {
        				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        				Date startDate = format.parse(jsonObject.getString("start_time")); // hardcode date for parsing
        				Date endDate = format.parse(jsonObject.getString("end_time"));
        				event.setStartDate(startDate);
	        			event.setEndDate(endDate);
	        			event.setTitle(jsonObject.getString("name"));
	        			event.setLocation(jsonObject.getString("rooms"));
	        			event.setHost(jsonObject.getString("member"));
	        			events.add(event);
        			}
        		}
        	}
		} catch (JSONException e) {
			Log.e(HACKER_DOJO, e.getMessage(), e);
		} catch (ParseException e) {
			Log.e(HACKER_DOJO, e.getMessage(), e);
		}
        Collections.sort(events);
        return events;
    }

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}
	
	@Override
	public void initializeView() {
    	setHeader("Events");
    	if(Calendar.getInstance().getTimeInMillis() - lastUpdated.getTimeInMillis() > 600000 /* 10 minutes */) {
	        lastUpdated = Calendar.getInstance();
	        
	        events = getEvents();
	        
	        startDates = new ArrayList<String>();
	        endDates = new ArrayList<String>();
	        titles = new ArrayList<String>();
	        Date lastDate = new Date(0);
    		Date today = new Date();
    		SimpleDateFormat format = new SimpleDateFormat("EEEE, MM/dd/yyyy");
        	TextView emptyMessage = (TextView) findViewById(R.id.empty);
	        if(events.size() == 0) {
	        	emptyMessage.setText("Check your network connection.");
	        	emptyMessage.setVisibility(View.VISIBLE);
	        	lastUpdated.setTime(new Date(0));
	        } else {
	        	emptyMessage.setVisibility(View.GONE);
	        }
	        for(int i=0; i<events.size(); i++) {
	        	Event event = events.get(i);
	        	if(event.getEndDate().before(new Date())) {
	        		continue;
	        	}
	        	if(event.getStartDate().getYear()>lastDate.getYear() || event.getStartDate().getMonth() > lastDate.getMonth() || event.getStartDate().getDate() > lastDate.getDate()) {
	        		startDates.add("");
	        		endDates.add("");
	        		Date date = event.getStartDate();
	        		if(date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDate() == today.getDate()) {
	        			titles.add("Today");
	        		} else {
	        			String dateString = format.format(date);
		        		titles.add(dateString);
	        		}
	        		lastDate = event.getStartDate();
	        	}
	        	String startMeridiem = "am";
	        	String endMeridiem = "am";
	        	int startHour = event.getStartDate().getHours();
	        	int endHour = event.getEndDate().getHours();
	        	if(startHour >= 12) {
	        		startMeridiem = "pm";
	        		startHour = startHour - 12;
	        	}
	        	if(endHour >= 12) {
	        		endMeridiem = "pm";
	        		endHour = endHour - 12;
	        	}
	        	if(startHour == 0) {
	        		startHour = 12;
	        	}
	        	if(endHour == 0) {
	        		endHour = 12;
	        	}
	        	startDates.add(String.format("%02d:%02d %s", startHour, event.getStartDate().getMinutes(),startMeridiem));
	        	endDates.add(String.format("%02d:%02d %s", endHour, event.getEndDate().getMinutes(),endMeridiem));
	        	titles.add(event.getTitle());
	        }
    	}
        EventArrayAdapter adapter2 = new EventArrayAdapter(this, startDates, endDates, titles);
        setListAdapter(adapter2);
        
        Button eventButton = (Button)findViewById(R.id.eventsButton);
        eventButton.setVisibility(View.GONE);
	}
}
