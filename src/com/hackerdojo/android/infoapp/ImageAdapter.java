package com.hackerdojo.android.infoapp; 
 
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
 
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
    
    
    /*Sorry about this!! Will work on getting it to be less confusing... This works in conjunction with
     * the maingrid.xml and HackerDojoActivity2 files to display the homescreen...
     */
 
    // create a new ImageView for each item referenced by the Adapter 
    public View getView(int position, View convertView, ViewGroup parent) { 
        ImageView imageView;
        
        if (convertView == null) {  // if it's not recycled, initialize some attributes 
            imageView = new ImageView(mContext); 
            
            imageView.setLayoutParams(new GridView.LayoutParams(96, 96)); //change numbers to reflect image scaling size 
                    
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); 
            imageView.setPadding(4, 36, 4, 36);
        } else { 
            imageView = (ImageView) convertView; 
        } 
 
        imageView.setImageResource(mThumbIds[position]); 
        return imageView; 
    } 
 
    // references to our images 
    private Integer[] mThumbIds = { 
    		
    		R.drawable.flag_with_border, //0
    		R.drawable.person_with_border, //1
    		R.drawable.nav_arrow_with_border, //2
    		R.drawable.money_bag_with_border, //3
    		

    }; 
} 