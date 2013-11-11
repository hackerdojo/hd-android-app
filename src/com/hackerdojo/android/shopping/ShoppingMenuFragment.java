package com.hackerdojo.android.shopping;

import com.hackerdojo.android.infoapp.R;

import android.R.integer;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingMenuFragment extends Fragment
{

	private ListView location_list;
	private String[] titles = {"Purchase Swag", "Donate"};
	private int[] images = {R.drawable.swag, R.drawable.donate};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub

			View view = inflater.inflate(R.layout.context_menu, container, false);

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
									openSwag();
									break;
								case 1:
									openGive();
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
				rowView = inflater.inflate(R.layout.menu_card, parent, false);

				TextView title;
				ImageView image;

				title = (TextView) rowView.findViewById(R.id.menu_card_tv);
				image = (ImageView) rowView.findViewById(R.id.menu_card_iv);
				
				if (position % 2 == 1) {
					title.setGravity(Gravity.RIGHT);
				}

				title.setText(titles[position]);
				image.setImageResource(images[position]);

				return rowView;

			}

	}

	private void openSwag()
		{
			String url = "http://www.hackerdojo.com/Swag";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		}
	
	private void openGive()
		{
			String url = "http://www.hackerdojo.com/Give";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		}


}
