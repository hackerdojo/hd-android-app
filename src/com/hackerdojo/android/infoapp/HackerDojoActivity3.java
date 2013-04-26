package com.hackerdojo.android.infoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerdojo.android.person.PersonCategoryActivity;

public class HackerDojoActivity3 extends Activity {


	public class MyAdapter extends BaseAdapter {

		final int NumberOfItem = 5;
		private Bitmap[] bitmap = new Bitmap[NumberOfItem];

		private Context context;
		private LayoutInflater layoutInflater;

		MyAdapter(Context c){
			context = c;
			layoutInflater = LayoutInflater.from(context);

			//init dummy bitmap,
			//using R.drawable.icon for all items
			for(int i = 0; i < NumberOfItem; i++){
				bitmap[i] = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bitmap.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return bitmap[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			View grid;
			if(convertView==null){
				grid = new View(context);
				grid = layoutInflater.inflate(R.layout.gridlayout, null); 
			}else{
				grid = (View)convertView; 
			}

			ImageView imageView = (ImageView)grid.findViewById(R.id.image);
			imageView.setImageResource(mThumbsIds[position]);
			TextView textView = (TextView)grid.findViewById(R.id.text);
			textView.setText(StringIds[position]);

			return grid;
		}

	}

	private Integer[] mThumbsIds = {
			R.drawable.flag_with_border, //0
			R.drawable.person_with_border, //1
			R.drawable.nav_arrow_with_border, //2
			R.drawable.money_bag_with_border, //3
			R.drawable.theme_with_border,//4
	};

	private String[] StringIds = {
			"Events",
			"People",
			"Navigate",
			"Purchase Swag",
			"Change Theme",
	};



	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maingrid);

		GridView gridView =(GridView)findViewById(R.id.grid);

		MyAdapter adapter = new MyAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			

		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			switch (position)
			{

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
			case 4: //Change Theme
				openTheme();
				break;

			}
		}
	});
	}

	private void openSwag(){
		Intent intent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("http://www.hackerdojo.com/Swag")
				);
		startActivity(intent);
	}

	private void openPerson(){
		Intent intent = new Intent(HackerDojoActivity3.this, PersonCategoryActivity.class);
		startActivity(intent);
	}

	private void openEvents() {
		Intent intent = new Intent(HackerDojoActivity3.this, EventActivity.class);
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
	
	private void openTheme() {
		Context context = getApplicationContext();
		CharSequence text = "Theme Support Coming Soon!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}



