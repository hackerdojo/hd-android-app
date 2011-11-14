package com.hackerdojo.android.infoapp;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class HackerDojoActivity extends ListActivity implements OnClickListener {
    private static final String HACKER_DOJO = "HACKER_DOJO";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	initializeView();
        Button evenButton = (Button) findViewById(R.id.eventsButton);
        evenButton.setOnClickListener(this);
        Button staffButton = (Button) findViewById(R.id.staffButton);
        staffButton.setOnClickListener(this);
        Button navigateButton = (Button) findViewById(R.id.navigateButton);
        navigateButton.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View v) {
		Log.e(HACKER_DOJO, "Button Click: " + v.getId());
		if(v.getId() == R.id.eventsButton) {
			openEvents();
		} else if(v.getId() == R.id.staffButton) {
			openStaff();
		} else if(v.getId() == R.id.navigateButton) {
			openNavigation();
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
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=140%20whisman%20rd%2C%20mountain%20view%2C%20ca"));
		startActivity(intent);
	}
	
	protected void setHeader(String headerString) {
		View dojoLogo = findViewById(R.id.imageView1);
        TextView header = (TextView) findViewById(R.id.header);
//    	if(this.getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_180 || this.getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_0) {
    	if(this.getWindowManager().getDefaultDisplay().getOrientation() == Surface.ROTATION_180 || this.getWindowManager().getDefaultDisplay().getOrientation() == Surface.ROTATION_0) {
    		dojoLogo.setVisibility(View.VISIBLE);
	        header.setText(headerString);
    	} else {
    		dojoLogo.setVisibility(View.GONE);
	        header.setText("Hacker Dojo " + headerString);
    	}
	}
	
	protected abstract void initializeView();
}
