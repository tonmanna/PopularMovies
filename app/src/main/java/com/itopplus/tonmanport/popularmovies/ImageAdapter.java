package com.itopplus.tonmanport.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itopplus.tonmanport.popularmovies.R;

import java.text.DateFormat;
import java.util.List;

import api.TheMoviesModelResultList;


/**
 * Created by Tonman on 26/8/2558.
 */
public class ImageAdapter extends BaseAdapter {
    private final String TAG = "ImageAdapterTAG";
    private Context mContext;
    private int layOutID;
    private List<TheMoviesModelResultList> theMovieResultItems;

    public ImageAdapter(Context mContext,int layOutID,List<TheMoviesModelResultList> theMovieResultItems) {
        this.mContext = mContext;
        this.layOutID = layOutID;
        this.theMovieResultItems = theMovieResultItems;
    }

    @Override
    public int getCount() {
        return theMovieResultItems.size();
    }

    public TheMoviesModelResultList getItem(int position) {
        return theMovieResultItems.get(position);
    }

    public long getItemId(int position) {
        return theMovieResultItems.get(position).id;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(layOutID,parent,false);
            Log.v(TAG, "Create View :" + position);
        } else {
            Log.d(TAG, "Recycle View :" + position);
        }

        TheMoviesModelResultList currentItem =  theMovieResultItems.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.grid_image_item);
        TextView textView = (TextView) view.findViewById(R.id.grid_text_item);
        TextView dateTextView = (TextView) view.findViewById(R.id.grid_date_item);

        textView.setText(currentItem.title);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
        dateTextView.setText(dateFormat.format(currentItem.release_date));
        String url = currentItem.poster_path;
        Glide.with(mContext)
                .load(url)
                //.diskCacheStrategy(DiskCacheStrategy.ALL) // Cache Everything
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .crossFade()
                .into(imageView);

        return view;
    }
}
