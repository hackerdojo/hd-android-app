package com.hackerdojo.android.person;

import com.hackerdojo.android.infoapp.HackerDojoActivity3;
import com.hackerdojo.android.infoapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PersonCategoryActivity extends Activity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.subpersonselector);
		
		Button staffButton = (Button) findViewById(R.id.staff_button);
		staffButton.setOnClickListener(this);
		
		Button memberButton = (Button) findViewById(R.id.member_button);
		memberButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		case R.id.staff_button:
			openStaff();
			break;
		case R.id.member_button:
			openMember();
			break;
		}
		
	}

	private void openMember() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(PersonCategoryActivity.this, PersonActivity.class);
		startActivity(intent);
		
	}

	private void openStaff() {

			Intent intent = new Intent(PersonCategoryActivity.this, StaffActivity.class);
			startActivity(intent);
		
	}

}
