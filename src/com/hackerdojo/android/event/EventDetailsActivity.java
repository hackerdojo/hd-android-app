package com.hackerdojo.android.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hackerdojo.android.infoapp.R;

public class EventDetailsActivity extends ActionBarActivity {

	
	String eventId;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subevent);
				
		Intent pIntent = getIntent();
		
		String message = pIntent.getStringExtra("message");
		eventId = pIntent.getStringExtra("event_id");

		TextView messagetext = (TextView) findViewById(R.id.message);
		messagetext.setText(message);
		
		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setNavigationMode(ActionBar.DISPLAY_HOME_AS_UP);

	}

	@Override
	public void onResume() 
	{
		super.onResume();

		Button calendarButton = (Button) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
					{
						// TODO Auto-generated method stub
						openCalendar();
					}
			});


	}

	public void openCalendar()
	{

		
		// clicking back for some reason still creates event in calendar?


		Intent calIntent = new Intent(Intent.ACTION_EDIT);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra("beginTime", EventsFragment.event.getStartDate().getTime());
		calIntent.putExtra("allDay", false);
		//calIntent.putExtra("description", EventsFragment.event.getLocation());
		calIntent.putExtra("endTime", EventsFragment.event.getEndDate().getTime());
		calIntent.putExtra("title", EventsFragment.event.getTitle());

		startActivity(calIntent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	
	private class DownloadEventDetailsTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params)
			{
				// TODO Auto-generated method stub
				Log.d("Get Request", "Get Request Here");

				HttpClient client = new DefaultHttpClient();

				try {
					String getURL = "http://events.hackerdojo.com/event/" + eventId + ".json";
					HttpGet get = new HttpGet(getURL);

					HttpResponse responseGet = client.execute(get);
					HttpEntity resEntityGet = responseGet.getEntity();

					if (resEntityGet != null) {
						InputStream stream = resEntityGet.getContent();
						String responseString = convertStreamToString(stream);
						Log.d("Downloaded Data", responseString);
						return responseString;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return "";
			}
		
		
		private String convertStreamToString(InputStream is)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try
					{
						while ((line = reader.readLine()) != null)
							{
								sb.append(line + "\n");
							}
					} catch (IOException e)
						{
							e.printStackTrace();
						}
				finally
				{
					try
						{
							is.close();
						}
					catch (IOException e)
						{
							e.printStackTrace();
						}
				}
				return sb.toString();
			}
		
	}
	

	
}