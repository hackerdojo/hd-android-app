package com.hackerdojo.android.infoapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HackerDojoActivity extends ListActivity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Events");
        
        String[] s = {"Wednesday: Nov 2, 2011", "6:00 pm - Example Meetup", "6:30 pm - Dojo Hacking", "Friday: Nov 4, 2011", "6:00 pm - Dojo Hour",
        "Wednesday: Nov 9, 2011", "6:00 pm - Example Meetup", "6:30 pm - Dojo Hacking", "Friday: Nov 11, 2011", "6:00 pm - Dojo Hour",
        "Wednesday: Nov 16, 2011", "6:00 pm - Example Meetup", "6:30 pm - Dojo Hacking", "Friday: Nov 18, 2011", "6:00 pm - Dojo Hour",
        "Wednesday: Nov 23, 2011", "6:00 pm - Example Meetup", "6:30 pm - Dojo Hacking", "Friday: Nov 25, 2011", "6:00 pm - Dojo Hour",
        "Wednesday: Nov 30, 2011", "6:00 pm - Example Meetup", "6:30 pm - Dojo Hacking", "Friday: Dec 2 , 2011", "6:00 pm - Dojo Hour"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.entry, s);
        setListAdapter(adapter);
    }

	@Override
	public void onClick(View v) {
		
	}
}