package com.hackerdojo.android.location;

import com.hackerdojo.android.infoapp.R;

import android.R.integer;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LocationMenuFragment extends Fragment
{

	private ListView location_list;
	private String[] titles = {"Navigate to the Dojo"};
	private int[] images = {R.drawable.hd_nav_graphic};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub

			View view = inflater.inflate(R.layout.location_menu, container, false);

			location_list = (ListView) view.findViewById(R.id.location_list);
			location_list.setAdapter(new CustomListAdapter(titles, images));

			location_list.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> arg0, View v,
							int position, long id)
						{
							// TODO Auto-generated method stub
							switch (position) {
								case 0:
									openNavigation();
									break;
							}
						}
				});

			return view;
		}

	public class CustomListAdapter extends BaseAdapter{

		String[] titles;
		int[] images;

		CustomListAdapter()
		{
			titles = null;
			images = null;
		}

		public CustomListAdapter(String[] text, int[] image) {
			titles = text;
			images = image;
		}

		@Override
		public int getCount()
			{
				// TODO Auto-generated method stub
				return titles.length;
			}

		@Override
		public Object getItem(int position)
			{
				// TODO Auto-generated method stub
				return position;
			}

		@Override
		public long getItemId(int position)
			{
				// TODO Auto-generated method stub
				return position;
			}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
			{
				// TODO Auto-generated method stub

				LayoutInflater inflater = getActivity().getLayoutInflater();
				View rowView;
				rowView = inflater.inflate(R.layout.location_menu_card, parent, false);

				TextView title;
				ImageView image;

				title = (TextView) rowView.findViewById(R.id.location_menu_tv);
				image = (ImageView) rowView.findViewById(R.id.location_menu_iv);

				title.setText(titles[position]);
				image.setImageResource(images[position]);

				return rowView;

			}

	}

	private void openNavigation()
		{
			Intent intent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("google.navigation:q=599%20Fairchild%20Dr%2C%20Mountain%20View%2C%20ca"));
			try
				{
					startActivity(intent);
				}
			catch (ActivityNotFoundException ex)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setCancelable(true);
					builder.setTitle("Hacker Dojo");
					String message = "599 Fairchild Dr\n"
							+ "Mountain View, CA 94041";
					builder.setMessage(message);
					builder.create().show();
				}
		}


}
