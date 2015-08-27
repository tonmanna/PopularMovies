package com.itopplus.tonmanport.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;


/**
 * Created by Tonman on 26/8/2558.
 */
public class ImageAdapter extends BaseAdapter {
    private final String TAG = "ImageAdapterTAG";
    private Context mContext;
    private int layOutID;
    private ArrayList<theMovieDBItems> theMovieDBItems;

    public ImageAdapter(Context mContext,int layOutID,ArrayList<theMovieDBItems> theMovieDBItems) {
        this.mContext = mContext;
        this.layOutID = layOutID;
        this.theMovieDBItems = theMovieDBItems;
    }

    @Override
    public int getCount() {
        return theMovieDBItems.size();
    }

    public theMovieDBItems getItem(int position) {
        return theMovieDBItems.get(position);
    }

    public long getItemId(int position) {
        return theMovieDBItems.get(position).ID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            imageView = (ImageView)(inflater.inflate(layOutID,parent,false).findViewById(R.id.grid_image_item));
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            Log.v(TAG, "Create View :" + position);
        } else {
            imageView = (ImageView) convertView;
            Log.d(TAG, "Recycle View :" + position);
        }

        String url = theMovieDBItems.get(position).url.toString();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(imageView);
        return imageView;
    }
}
