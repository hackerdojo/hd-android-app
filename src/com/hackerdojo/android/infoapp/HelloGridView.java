package com.hackerdojo.android.infoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class HelloGridView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
        setContentView(R.layout.maingrid);
        
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        
        gridview.setOnItemClickListener(new OnItemClickListener()
        {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        	{
        		switch (position){
        		
        		case 0: //Event
        			openEvents();
        			break;
        		case 1: //Staff Clock In
        			openStaff();
        			break;
        		case 2: //Navigate 
        			openNavigation();
        			break;
        			       		
        		}
        		//Toast.makeText(HelloGridView.this, "index = " + position, Toast.LENGTH_SHORT).show();
        		
        	}
        }
        );
    }
    
    
	private void openEvents() {
		Intent intent = new Intent(HelloGridView.this, EventActivity.class);
		startActivity(intent);
	}

	private void openStaff() {
		Intent intent = new Intent(HelloGridView.this, StaffActivity.class);
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