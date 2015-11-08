package com.itopplus.tonmanport.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import api.TheMoviesReviewResultList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tonman on 8/11/2558.
 */
public class AdapterListViewReview extends ArrayAdapter<TheMoviesReviewResultList> {

    List<TheMoviesReviewResultList> objects;
    int resource;
    public AdapterListViewReview(Context context, int resource, List<TheMoviesReviewResultList> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;
    }

    @Bind(R.id.auther_text)
    TextView tvAuther;

    @Bind(R.id.content_text)
    TextView tvContent;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }else{

        }
        TheMoviesReviewResultList i = objects.get(position);
        ButterKnife.bind(this, v);
        if (i != null) {
            tvAuther.setText(i.author);
            tvContent.setText(i.content);
        }
        // the view must be returned to our activity
        return v;
    }
}
