package com.itopplus.tonmanport.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import api.TheMoviesVideoResultList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tonman on 8/11/2558.
 */
public class AdapterListViewTrailer extends ArrayAdapter<TheMoviesVideoResultList> {

    List<TheMoviesVideoResultList> objects;
    Context mContext;
    int resource;
    public AdapterListViewTrailer(Context context, int resource, List<TheMoviesVideoResultList> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.objects = objects;
    }
    @Bind(R.id.video_link)
    TextView textViewVideoLink;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }else{

        }
        final TheMoviesVideoResultList i = objects.get(position);
        ButterKnife.bind(this, v);
        if (i != null) {
            final String CurrentURL = "http://www.youtube.com/v/"+i.key;
            String textURL = "<a href=\""+CurrentURL+"\">Video"+(position+1)+"</a>";
            Spanned htmlText= Html.fromHtml(textURL);
            textViewVideoLink.setText(htmlText);
            textViewVideoLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("vnd.youtube:"+ i.key);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("VIDEO_ID",i.key);
                    mContext.startActivity(intent);
                }
            });
        }
        // the view must be returned to our activity
        return v;
    }
}
