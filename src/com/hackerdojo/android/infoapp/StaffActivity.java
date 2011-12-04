package com.hackerdojo.android.infoapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StaffActivity extends HackerDojoActivity implements
		OnClickListener {

	private final static AtomicReference<Calendar> lastChecked = new AtomicReference<Calendar>();
	private final static AtomicReference<List<Person>> staff = new AtomicReference<List<Person>>(new ArrayList<Person>());
	
	static {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(0));
		lastChecked.set(cal);
	}

	private final static String staffUrl = "http://hackerdojo-signin.appspot.com/staffjson";
	
	@Override
	public void onStart() {
		super.onStart();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onResume() {
		super.onResume();

		Button staffButton = (Button) findViewById(R.id.staffButton);
		staffButton.setVisibility(View.GONE);
		
		// strange bug, events properly maintains state but staff doesn't

		if (Calendar.getInstance().getTimeInMillis() - lastChecked.get().getTimeInMillis() > (1000 * 60 * 10)) { // 10 minutes
			new UpdateStaffTask(this).execute(staffUrl);
			TextView emptyText = (TextView) findViewById(R.id.empty);
			emptyText.setText("Loading...");
			emptyText.setVisibility(View.VISIBLE);
		} else {
			new UpdateStaffView(this).execute(StaffActivity.staff.get());
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	private class UpdateStaffTask extends JsonUpdateTask<Person> {
		private Activity activity;
		public UpdateStaffTask(Activity activity) {
			this.activity = activity;
		}
		@Override
		public List<Person> transform(String string) {
			if (string == null || string.trim().length() == 0) {
				return new ArrayList<Person>();
			}
	        ArrayList<Person> staff = new ArrayList<Person>();
	        try {
	        	JSONArray json = new JSONArray(string);
	        	for(int i=0; i<json.length(); i++) {
	        		Person person = new Person();
	        		JSONObject jsonObject = json.getJSONObject(i);
	        		if(jsonObject.has("name") && jsonObject.has("created") && jsonObject.has("image_url") && jsonObject.has("name")) {
	    				SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	    				
	        			String name = jsonObject.getString("name");
	        			Log.v(TAG, "created: " + jsonObject.getString("created"));
	    				Date created = format.parse(jsonObject.getString("created")); // hardcode date for parsing
	        			String imageUrl = jsonObject.getString("image_url");
	        			
	    				person.setName(name);
	        			person.setCreated(created);
	        			person.setImageUrl(imageUrl);
	        			staff.add(person);
	        		}
	        	}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			} catch (ParseException e) {
				Log.e(TAG, e.getMessage(), e);
			}
	        Collections.sort(staff);
			return staff;
		}

		@SuppressWarnings("unchecked")
		public void onPostExecute(List<Person> staff) {
			StaffActivity.staff.set(staff);
			new UpdateStaffView(activity).execute(StaffActivity.staff.get());
		}
	}
	
	private class UpdateStaffView extends AsyncTask<List<Person>, Void, List<Person>> {
		private Activity activity;
		public UpdateStaffView(Activity activity) {
			this.activity = activity;
		}
		
		protected List<Person> doInBackground(List<Person>... params) {
			return params[0];
		}
		
		public void onPostExecute(List<Person> staff) {
			Log.i(HackerDojoActivity.TAG, "staff size: " + staff.size());
			
	        Calendar lastUpdated = Calendar.getInstance();
	        
	        ArrayList<String> names = new ArrayList<String>();
	        ArrayList<String> timeHere = new ArrayList<String>();
	        ArrayList<String> images = new ArrayList<String>();
	        for(int i=0; i<staff.size(); i++) {
	        	Person person = staff.get(i);
	        	Calendar timeEntered = Calendar.getInstance();
	        	timeEntered.setTime(person.getCreated());
	        	
	        	long timeRadix = lastUpdated.getTimeInMillis() - timeEntered.getTimeInMillis();
	        	
	        	// normalize for timezone
	        	
	        	long offset = TimeZone.getDefault().getRawOffset();
	        	timeRadix = timeRadix - offset;
	        	
	        	timeRadix = timeRadix / 60;
	        	timeRadix = timeRadix / 1000;
	        	
	        	// time is now UTC
	        	
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
	        
	        StaffArrayAdapter adapter2 = new StaffArrayAdapter(activity, images, names, timeHere);
	        setListAdapter(adapter2);
	
			TextView emptyText = (TextView) findViewById(R.id.empty);
			// update calendar
			if (staff.size() > 0) {
				emptyText.setVisibility(View.GONE);
				Calendar cal = Calendar.getInstance();
				lastChecked.set(cal);
			} else {
				emptyText.setText("Dojo is closed");
				emptyText.setVisibility(View.VISIBLE);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(0));
				lastChecked.set(cal);
			}
		}
	}
}
