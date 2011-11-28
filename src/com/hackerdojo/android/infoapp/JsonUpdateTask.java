package com.hackerdojo.android.infoapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public abstract class JsonUpdateTask<T> extends AsyncTask<String, Long, List<T>> {
	public String fetchFromUrl(String url) {
        HttpClient client = new DefaultHttpClient();
        StringBuffer sb = new StringBuffer();
        try {
            Log.i(HackerDojoActivity.TAG, "FETCHING: " + url);
            HttpResponse response = client.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            char[] buf = new char[1024];
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            int c=0;
            while(c != -1) {
                c = reader.read(buf);
                if(c > 0) {
                    sb.append(buf, 0, c);
                }
            }
        } catch (ClientProtocolException e) {
            Log.e(HackerDojoActivity.TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.e(HackerDojoActivity.TAG, e.getMessage(), e);
        }
		return sb.toString();
	}

	@Override
	protected List<T> doInBackground(String... params) {
		return transform(fetchFromUrl(params[0]));
	}

	public abstract List<T> transform(String json);
}
