package com.hackerdojo.android.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hackerdojo.android.infoapp.R;

public class EventDetails extends ActionBarActivity implements OnClickListener{


	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subevent);
				
		Intent pIntent = getIntent();
		
		String message = pIntent.getStringExtra("message");

		TextView messagetext = (TextView) findViewById(R.id.message);
		messagetext.setText(message);
	}

	@Override
	public void onResume() 
	{
		super.onResume();

		Button calendarButton = (Button) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) 
	{

		switch(v.getId())
		{
		case R.id.calendarButton:
			openCalendar();
		}
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
	

	
}