package com.hackerdojo.android.location;

import com.hackerdojo.android.infoapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationMenuFragment extends Fragment
	{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			
			View view = inflater.inflate(R.layout.location_menu, container, false);
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		
		
		
	}
