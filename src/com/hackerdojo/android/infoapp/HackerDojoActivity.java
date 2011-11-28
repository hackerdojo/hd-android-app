package com.hackerdojo.android.infoapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class HackerDojoActivity extends ListActivity implements
		OnClickListener {
	public static final String TAG = "HackerDojoActivity";

	public static final Executor tasks = Executors.newSingleThreadExecutor();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onResume() {
		super.onResume();

		Button eventsButton = (Button) findViewById(R.id.eventsButton);
		eventsButton.setOnClickListener(this);

		Button staffButton = (Button) findViewById(R.id.staffButton);
		staffButton.setOnClickListener(this);

		Button navigateButton = (Button) findViewById(R.id.navigateButton);
		navigateButton.setOnClickListener(this);
		
		// hide dojo logo when sideways, takes up a lot of room
		View dojoLogo = findViewById(R.id.imageView1);
		if(this.getWindowManager().getDefaultDisplay().getOrientation() == Surface.ROTATION_180 || this.getWindowManager().getDefaultDisplay().getOrientation() == Surface.ROTATION_0) {
    		dojoLogo.setVisibility(View.VISIBLE);
    	} else {
    		dojoLogo.setVisibility(View.GONE);
    	}	
		
	}

	@Override
	public void onClick(View v) {
		Log.i(TAG, "Button Click: " + v.getId());
		switch (v.getId()) {
		case R.id.eventsButton:
			openEvents();
			break;
		case R.id.staffButton:
			openStaff();
			break;
		case R.id.navigateButton:
			openNavigation();
			break;
		default:

		}
	}

	private void openEvents() {
		Intent intent = new Intent(HackerDojoActivity.this, EventActivity.class);
		startActivity(intent);
	}

	private void openStaff() {
		Intent intent = new Intent(HackerDojoActivity.this, StaffActivity.class);
		startActivity(intent);
	}

	private void openNavigation() {
		Intent intent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=140%20whisman%20rd%2C%20mountain%20view%2C%20ca"));
		startActivity(intent);
	}
}
