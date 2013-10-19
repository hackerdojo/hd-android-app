package com.hackerdojo.android.infoapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class HackerDojoActivity extends ListFragment {
	public static final String TAG = "HackerDojoActivity";

	public static final Executor tasks = Executors.newSingleThreadExecutor();

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main, container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		
	}
}
