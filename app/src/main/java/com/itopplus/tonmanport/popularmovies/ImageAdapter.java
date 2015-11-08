package com.itopplus.tonmanport.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.List;

import api.TheMoviesResultList;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Tonman on 26/8/2558.
 */
public class ImageAdapter extends BaseAdapter {
    private final String TAG = "ImageAdapterTAG";
    private Context mContext;
    private int layOutID;
    private List<TheMoviesResultList> theMovieResultItems;

    @Bind(R.id.grid_image_item) ImageView imageView;
    @Bind(R.id.grid_text_item) TextView textView;
    @Bind(R.id.grid_date_item) TextView dateTextView;

    public ImageAdapter(Context mContext,int layOutID,List<TheMoviesResultList> theMovieResultItems) {
        this.mContext = mContext;
        this.layOutID = layOutID;
        this.theMovieResultItems = theMovieResultItems;
    }

    @Override
    public int getCount() {
        return theMovieResultItems.size();
    }

    public TheMoviesResultList getItem(int position) {
        return theMovieResultItems.get(position);
    }

    public long getItemId(int position) {
        return theMovieResultItems.get(position).id;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(layOutID, parent,false);
        } else {

        }
        ButterKnife.bind(this, view);

        TheMoviesResultList currentItem =  theMovieResultItems.get(position);

        textView.setText(currentItem.title);
        try {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
            dateTextView.setText(dateFormat.format(currentItem.release_date));
        }catch(Exception ex){}
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
