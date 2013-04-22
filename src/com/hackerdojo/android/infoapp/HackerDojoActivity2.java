package com.hackerdojo.android.infoapp;

import com.hackerdojo.android.person.PersonActivity;
import com.hackerdojo.android.person.PersonCategoryActivity;
import com.hackerdojo.android.person.StaffActivity;

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


public class HackerDojoActivity2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
        setContentView(R.layout.maingrid);
        
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        //gridview.setSelector(android.R.color.transparent);
        
        /*Sorry about this!! Will work on getting it to be less confusing... This works in conjunction with
         * the maingrid.xml and ImageAdapter files to display the homescreen...
         */
        
        gridview.setOnItemClickListener(new OnItemClickListener()
        {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        	{
        		switch (position){
        		
        		case 0: //Event
        			openEvents();
        			break;
        		case 1: //Person
        			openPerson();
        			break;
        		case 2: //Navigate 
        			openNavigation();
        			break;
        		case 3: //Purchase Swag
        			openSwag();
        			break;
        			       		
        		}
        		//Toast.makeText(HelloGridView.this, "index = " + position, Toast.LENGTH_SHORT).show();
        		
        	}
        }
        );
    }
    
    private void openSwag(){
    	Intent intent = new Intent(
    			Intent.ACTION_VIEW,
    			Uri.parse("http://www.hackerdojo.com/Swag")
    			);
    	startActivity(intent);
    }
    
    private void openPerson(){
    	Intent intent = new Intent(HackerDojoActivity2.this, PersonCategoryActivity.class);
    	startActivity(intent);
    }
    
	private void openEvents() {
		Intent intent = new Intent(HackerDojoActivity2.this, EventActivity.class);
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