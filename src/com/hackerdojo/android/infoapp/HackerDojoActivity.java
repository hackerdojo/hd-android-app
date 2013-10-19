package com.hackerdojo.android.infoapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;

import com.hackerdojo.android.event.EventActivity;

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
	
		
	}

	@Override
	public void onClick(View v) {
		Log.i(TAG, "Button Click: " + v.getId());
		switch (v.getId()) {
		default:

		}
	}

	private void openEvents() {
		Intent intent = new Intent(HackerDojoActivity.this, EventActivity.class);
		startActivity(intent);
	}


	private void openNavigation() {
		Intent intent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=599%20Fairchild%20Dr%2C%20Mountain%20View%2C%20ca"));
		try {
			startActivity(intent);
		} catch(ActivityNotFoundException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					this);
			builder.setCancelable(true);
			builder.setTitle("Hacker Dojo");
			String message = 
					"599 Fairchild Dr\n" +
					"Mountain View, CA 94041";
			builder.setMessage(message);
			builder.create().show();
		}
	}
}
