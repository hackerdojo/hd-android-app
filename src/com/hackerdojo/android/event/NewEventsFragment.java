package com.hackerdojo.android.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.mipmap;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackerdojo.android.infoapp.HackerDojoMainActivity;
import com.hackerdojo.android.infoapp.JsonUpdateTask;
import com.hackerdojo.android.infoapp.R;

public class NewEventsFragment extends ListFragment
{

	private String message;
	public static Event event;

	ArrayList<Event> events;

	private View view;
	
	private EventListAdapter mAdapter;


	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_events, container, false);

		events = new ArrayList<Event>();

		DownloadEventsTask downloadEventsTask = new DownloadEventsTask();
		downloadEventsTask.execute();
		
//		getListView().setOnItemClickListener(new OnItemClickListener()
//			{
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id)
//					{
//						// TODO Auto-generated method stub
//						Log.d("Click detected at position", String.valueOf(position));
//						
//						Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
//						
//						intent.putExtra("message", message);
//						intent.putExtra("details", event.getDescription());
//						
//						startActivity(intent);
//					}
//			});

		this.setRetainInstance(true);
		return view;
	}

	//	@SuppressWarnings("unchecked")
	//	@Override
	//	public void onResume() 
	//	{
	//		super.onResume();
	//
	//
	//
	//		if (Calendar.getInstance().getTimeInMillis() - lastChecked.get().getTimeInMillis() > (1000 * 60 * 10)) //10 minutes
	//		{
	//			new UpdateEventsTask(this).execute("http://events.hackerdojo.com/events.json");
	//			TextView emptyText = (TextView) view.findViewById(R.id.empty);
	//			emptyText.setText("Loading...");
	//			emptyText.setVisibility(View.VISIBLE);
	//		}
	//		else 
	//		{
	//			new UpdateEventsView(this).execute(NewEventsFragment.events.get());
	//		}


	//	private class UpdateEventsTask extends JsonUpdateTask<Event> 
	//	{
	//		
	//		private Fragment fragment;
	//
	//		public UpdateEventsTask(Fragment fragment) 
	//		{
	//			this.fragment = fragment;
	//		}
	//
	//		@Override
	//		public List<Event> transform(String string) {
	//			if (string == null || string.trim().length() == 0) {
	//				return new ArrayList<Event>();
	//			}
	//			ArrayList<Event> events = new ArrayList<Event>();
	//			try 
	//			{
	//				JSONArray json = new JSONArray(string);
	//				for (int i = 0; i < json.length(); i++) 
	//				{
	//					Event event = new Event();
	//					JSONObject jsonObject = json.getJSONObject(i);
	//					if (jsonObject.has("status")
	//							&& jsonObject.has("start_time")
	//							&& jsonObject.has("end_time")
	//							&& jsonObject.has("name")
	//							&& jsonObject.has("member")
	//							&& jsonObject.has("rooms")
	//							&& jsonObject.has("id")) 
	//					{
	//						if (jsonObject.getString("status").equals("approved")) 
	//						{
	//							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	//							Date startDate = format.parse(jsonObject.getString("start_time")); // hardcode date for parsing
	//							Date endDate = format.parse(jsonObject.getString("end_time"));
	//							event.setStartDate(startDate);
	//							event.setEndDate(endDate);
	//							event.setTitle(jsonObject.getString("name"));
	//							event.setLocation(jsonObject.getString("rooms"));
	//							event.setHost(jsonObject.getString("member"));
	//							event.setId(jsonObject.getInt("id"));
	//							if(jsonObject.has("estimated_size")) 
	//							{
	//								event.setSize(jsonObject.getInt("estimated_size"));
	//							}
	//							events.add(event);														
	//						}
	//					}
	//				}
	//			} 
	//			catch (JSONException e) 
	//			{
	//				Log.e(HackerDojoMainActivity.TAG, e.getMessage(), e);
	//			} 
	//			catch (ParseException e) 
	//			{
	//				Log.e(HackerDojoMainActivity.TAG, e.getMessage(), e);
	//			}
	//			Collections.sort(events);
	//			return events;
	//		}
	//
	//		@SuppressWarnings("unchecked")
	//		public void onPostExecute(List<Event> events) 
	//		{
	////			NewEventsFragment.events.set(events);
	////			new UpdateEventsView(fragment).execute(NewEventsFragment.events.get());
	//		}
	//	}





	//	private class UpdateEventsView extends AsyncTask<List<Event>, Void, List<Event>> 
	//	{
	//
	//		private Fragment fragment;
	//
	//		public UpdateEventsView(Fragment fragment) 
	//		{
	//			this.fragment = fragment;
	//		}
	//
	//		@Override
	//		protected List<Event> doInBackground(List<Event>... params) 
	//		{
	//			return params[0];
	//		}
	//
	//		@Override
	//		public void onPostExecute(List<Event> events) 
	//		{
	//			Log.i(HackerDojoMainActivity.TAG, "event size: " + events.size());
	//			Calendar lastUpdated = Calendar.getInstance();
	//
	//			ArrayList<String> startDates = new ArrayList<String>();
	//			ArrayList<String> endDates = new ArrayList<String>();
	//			ArrayList<String> titles = new ArrayList<String>();
	//			Date lastDate = new Date(0);
	//			Date today = new Date();
	//			SimpleDateFormat format = new SimpleDateFormat("EEEE, MM/dd/yyyy");
	//			TextView emptyMessage = (TextView) view.findViewById(R.id.empty);
	//			Map<Integer, Event> eventMapping = new HashMap<Integer, Event>();
	//			if (events.size() == 0) 
	//			{
	//				emptyMessage.setText("Check your network connection.");
	//				emptyMessage.setVisibility(View.VISIBLE);
	//				lastUpdated.setTime(new Date(0));
	//			}
	//			else 
	//			{
	//				emptyMessage.setVisibility(View.GONE);
	//			}
	//			for (int i = 0; i < events.size(); i++) 
	//			{
	//				Event event = events.get(i);
	//				if (event.getEndDate().before(new Date())) 
	//				{
	//					continue;
	//				}
	//				if (event.getStartDate().getYear() > lastDate.getYear()
	//						|| event.getStartDate().getMonth() > lastDate
	//						.getMonth()
	//						|| event.getStartDate().getDate() > lastDate.getDate()) 
	//				{
	//					startDates.add("");
	//					endDates.add("");
	//					Date date = event.getStartDate();
	//					if (date.getYear() == today.getYear()
	//							&& date.getMonth() == today.getMonth()
	//							&& date.getDate() == today.getDate()) {
	//						titles.add("Today");
	//					} 
	//					else 
	//					{
	//						String dateString = format.format(date);
	//						titles.add(dateString);
	//					}
	//					lastDate = event.getStartDate();
	//				}
	//				String startMeridiem = "am";
	//				String endMeridiem = "am";
	//				int startHour = event.getStartDate().getHours();
	//				int endHour = event.getEndDate().getHours();
	//				if (startHour >= 12) {
	//					startMeridiem = "pm";
	//					startHour = startHour - 12;
	//				}
	//				if (endHour >= 12) 
	//				{
	//					endMeridiem = "pm";
	//					endHour = endHour - 12;
	//				}
	//				if (startHour == 0) 
	//				{
	//					startHour = 12;
	//				}
	//				if (endHour == 0) 
	//				{
	//					endHour = 12;
	//				}
	//				startDates.add(String.format("%02d:%02d %s", startHour, event
	//						.getStartDate().getMinutes(), startMeridiem));
	//				endDates.add(String.format("%02d:%02d %s", endHour, event
	//						.getEndDate().getMinutes(), endMeridiem));
	//				titles.add(event.getTitle());
	//				eventMapping.put(titles.size() - 1, event);
	//			}
	//
	//			eventIndexes.set(eventMapping);
	//
	//			EventArrayAdapter adapter2 = new EventArrayAdapter(getActivity(), startDates, endDates, titles);
	//			setListAdapter(adapter2);
	//
	//			ListView lv = getListView();
	//			lv.setOnItemClickListener(new OnItemClickListener() 
	//			{
	//				@Override
	//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	//				{
	//					Map<Integer, Event> map = NewEventsFragment.eventIndexes.get();
	//
	//					if(map.containsKey(position)) 
	//					{
	//
	//						event = map.get(position);	
	//						
	//						String specificeventUrl = "http://events.hackerdojo.com/event/" + event.getId() + ".json";
	//						//new UpdateSpecificEventTask().execute(specificeventUrl);
	//						JSONObject specificJSON = new JSONObject();
	//						
	//						Log.d("specificJSON", specificJSON.toString());
	//
	//						String formatted_location = event.getLocation();
	//						formatted_location = formatted_location.replace("[", "");
	//						formatted_location = formatted_location.replace("]", "");
	//
	//						message = String.format(
	//								"Starts:\n%s\n\nEnds:\n%s\n\nLocated at:\n%s\n\nHosted by:\n%s",
	//								event.getStartDate(), event.getEndDate(),
	//								formatted_location, event.getHost());
	//
	//						if(event.getSize() > 0) 
	//						{
	//							message = message + "\n\nestimated size:\n" + event.getSize();
	//						}
	//						
	//
	//
	//						//message = message + "\n\nDescription:\n" + event.getDescription();
	//
	//
	//						Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
	//						
	//						intent.putExtra("message", message);
	//						intent.putExtra("details", event.getDescription());
	//						
	//						startActivity(intent);
	//					}
	//				}
	//
	//
	//
	//			});
	//
	//			TextView emptyText = (TextView) view.findViewById(R.id.empty);
	//			// update calendar
	//			if (events.size() > 0) {
	//				emptyText.setVisibility(View.GONE);
	//				Calendar cal = Calendar.getInstance();
	//				lastChecked.set(cal);
	//			} else {
	//				emptyText.setText("Unable to load events.");
	//				emptyText.setVisibility(View.VISIBLE);
	//				Calendar cal = Calendar.getInstance();
	//				cal.setTime(new Date(0));
	//				lastChecked.set(cal);
	//			}
	//		}
	//	}

	private class DownloadEventsTask extends AsyncTask<String, String, String> {


		@Override
		protected String doInBackground(String... params)
			{
				// TODO Auto-generated method stub
				Log.d("Get Request", "Get Request Here");

				HttpClient client = new DefaultHttpClient();

				try {
					String getURL = "http://events.hackerdojo.com/events.json";
					HttpGet get = new HttpGet(getURL);

					HttpResponse responseGet = client.execute(get);
					HttpEntity resEntityGet = responseGet.getEntity();

					if (resEntityGet != null) {
						InputStream stream = resEntityGet.getContent();
						String responseString = convertStreamToString(stream);
						Log.d("Downloaded Data", responseString);
						return responseString;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return "";
			}

		private String convertStreamToString(InputStream is)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try
					{
						while ((line = reader.readLine()) != null)
							{
								sb.append(line + "\n");
							}
					} catch (IOException e)
						{
							e.printStackTrace();
						}
				finally
				{
					try
						{
							is.close();
						}
					catch (IOException e)
						{
							e.printStackTrace();
						}
				}
				return sb.toString();
			}


		@Override
		protected void onPostExecute(String result)
			{
				try {

					JSONArray jsonArray = new JSONArray(result);

					for (int i = 0; i<jsonArray.length(); i++) {
						Event event = new Event();

						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						Date startDate = format.parse(jsonArray.getJSONObject(i).getString("start_time")); // hardcode date for parsing
						Date endDate = format.parse(jsonArray.getJSONObject(i).getString("end_time"));
						event.setStartDate(startDate);
						event.setEndDate(endDate);
						event.setTitle(jsonArray.getJSONObject(i).getString("name"));
						event.setLocation(jsonArray.getJSONObject(i).getString("rooms"));
						event.setHost(jsonArray.getJSONObject(i).getString("member"));
						event.setId(jsonArray.getJSONObject(i).getInt("id"));
						if(jsonArray.getJSONObject(i).has("estimated_size")) 
							{
								event.setSize(jsonArray.getJSONObject(i).getInt("estimated_size"));
							}

						events.add(event);										

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				catch (ParseException e)
					{
						e.printStackTrace();
					}

				//				EventArrayAdapter adapter2 = new EventArrayAdapter(getActivity(), startDates, endDates, titles);
				//				setListAdapter(adapter2)
				
				mAdapter = new EventListAdapter(events);
				setListAdapter(mAdapter);
				


			}
	}
	
	private class EventListAdapter extends BaseAdapter {
		
		ArrayList<Event> mItems;

		public EventListAdapter(ArrayList<Event> items) {
			mItems = items;
		}
		
		@Override
		public int getCount()
			{
				// TODO Auto-generated method stub
				return mItems.size();
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
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View rowView;
				
				rowView = inflater.inflate(R.layout.event, parent, false);
				
				TextView eventTitleTextView = (TextView) rowView.findViewById(R.id.event_title_tv);
				eventTitleTextView.setText(mItems.get(position).getTitle());
				
//				TextView startTimeTextView = (TextView) rowView.findViewById(R.id.event_start_time_tv);
//				startTimeTextView.setText(mItems.get(position).getStartDate());
				
				return rowView;
			}
		
		
	}
	
	
}


