package com.itopplus.tonmanport.popularmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.text.DateFormat;

import api.TheMoviesModelResultList;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TheMoviesModelResultList movies_result = (TheMoviesModelResultList)getIntent().getSerializableExtra("movies_result");

        TextView tvHead1 = (TextView)findViewById(R.id.detail_text_head);
        TextView tvHead2 = (TextView)findViewById(R.id.detail_text_head2);
        TextView tvContent = (TextView)findViewById(R.id.detail_text_content);
        ImageView imageView = (ImageView)findViewById(R.id.detail_image_content);

        tvHead1.setText(movies_result.title);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getApplicationContext());
        String textRow = "Vote :" + movies_result.vote_average + "/10 Release date : "+dateFormat.format(movies_result.release_date);
        tvHead2.setText(textRow);

        tvContent.setText(movies_result.overview);

        Glide.with(this)
                .load(movies_result.poster_path_hires)
                .centerCrop()
                .into(imageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
