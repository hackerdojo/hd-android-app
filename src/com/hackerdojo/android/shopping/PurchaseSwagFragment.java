package com.hackerdojo.android.shopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hackerdojo.android.infoapp.R;

public class PurchaseSwagFragment extends Fragment
	{
		private WebView webView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			View view = inflater.inflate(R.layout.fragment_swag, container, false);
			
			webView = (WebView) view.findViewById(R.id.swag_wv);
			
			WebSettings webSettings = webView.getSettings();
			
			webView.loadUrl("http://www.hackerdojo.com/Swag");
			webSettings.setJavaScriptEnabled(true);
			webSettings.setBuiltInZoomControls(true);
			
			return view;
		}

	}
