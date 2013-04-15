package com.hackerdojo.android.infoapp; 
 
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 
public class ImageAdapter extends BaseAdapter { 
    private Context mContext; 
 
    public ImageAdapter(Context c) { 
        mContext = c; 
    } 
 
    public int getCount() { 
        return mThumbIds.length; 
    } 
 
    public Object getItem(int position) { 
        return null; 
    } 
 
    public long getItemId(int position) { 
        return 0; 
    } 
    
 
    // create a new ImageView for each item referenced by the Adapter 
    public View getView(int position, View convertView, ViewGroup parent) { 
        ImageView imageView;        
        
        if (convertView == null) {  // if it's not recycled, initialize some attributes 
            imageView = new ImageView(mContext); 
            imageView.setLayoutParams(new GridView.LayoutParams(128, 128)); //change numbers to reflect image scaling size 
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); 
            imageView.setPadding(8, 8, 8, 8); 
        } else { 
            imageView = (ImageView) convertView; 
        } 
 
        imageView.setImageResource(mThumbIds[position]); 
        return imageView; 
    } 
 
    // references to our images 
    private Integer[] mThumbIds = { 
    		
    		R.drawable.event_flag_with_text, R.drawable.staff_with_text, R.drawable.compass_with_text

    }; 
} 