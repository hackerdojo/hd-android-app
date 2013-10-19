package com.hackerdojo.android.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hackerdojo.android.infoapp.R;
import com.hackerdojo.android.infoapp.R.id;
import com.hackerdojo.android.infoapp.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EventSubActivity extends Activity implements OnClickListener{


	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subevent);

		TextView messagetext = (TextView) findViewById(R.id.message);
		messagetext.setText(EventActivity.message);
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

		
		//need to find a way to display information properly...


		Intent calIntent = new Intent(Intent.ACTION_EDIT);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra("beginTime", EventActivity.event.getStartDate().getTime());
		calIntent.putExtra("allDay", false);
		calIntent.putExtra("description", EventActivity.event.getLocation());
		calIntent.putExtra("endTime", EventActivity.event.getEndDate().getTime());
		calIntent.putExtra("title", EventActivity.event.getTitle());

		startActivity(calIntent);
	}
	

	
}