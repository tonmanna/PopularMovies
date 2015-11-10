package com.itopplus.tonmanport.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.text.DateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import api.TheMoviesResultList;
import api.TheMoviesReviewModel;
import api.TheMoviesVideoModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import lib.Utility;

public class MovieDetailActivity extends AppCompatActivity {


    private int current_page = 1;
    private boolean bCheckFavorite = false;
    @Bind(R.id.detail_text_head) TextView tvHead1;
    @Bind(R.id.detail_text_head2) TextView tvHead2;
    @Bind(R.id.detail_text_content) TextView tvContent;
    @Bind(R.id.detail_image_content) ImageView imageView;

    @Bind(R.id.listview_review)
    GridView gridViewReview;

    @Bind(R.id.listview_video)
    GridView gridViewVideo;

    @Bind(R.id.favorite_checkbox)
    CheckBox cbFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final TheMoviesResultList movies_result = (TheMoviesResultList)intent.getSerializableExtra("movies_result");
        current_page = intent.getIntExtra("current_page",1);
        tvHead1.setText(movies_result.title);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getApplicationContext());
        String textRow = "Vote :" + movies_result.vote_average + "/10 Release date : "+dateFormat.format(movies_result.release_date);
        tvHead2.setText(textRow);

        tvContent.setText(movies_result.overview);

        Glide.with(this)
                .load(movies_result.poster_path_hires)
                .centerCrop()
                .into(imageView);
        FetchVideoAsyncTask fetchVideo = new FetchVideoAsyncTask(getApplicationContext(),gridViewVideo);
        TheMoviesVideoModel apiVideo = new TheMoviesVideoModel();
        apiVideo.id = movies_result.id;
        fetchVideo.execute(apiVideo);

        FetchReviewAsyncTask fetchReview = new FetchReviewAsyncTask(getApplicationContext(),gridViewReview);
        TheMoviesReviewModel apiReview = new TheMoviesReviewModel();
        apiReview.id = movies_result.id;
        fetchReview.execute(apiReview);


        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Set<String> restoredText = sharedPrefs.getStringSet(Utility.MoviesIDList, new HashSet<String>());
        for(String restoreMovieID:restoredText){
            if(restoreMovieID.equals(Long.toString(movies_result.id))) {
                bCheckFavorite = true;
                cbFavorite.setChecked(bCheckFavorite);
                break;
            }
        }

        cbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      SharedPreferences.Editor editor = sharedPrefs.edit();
                                                      Set<String> inText = new HashSet<String>(restoredText);
                                                      if(isChecked) {
                                                          inText.add(Long.toString(movies_result.id));
                                                          editor.putStringSet(Utility.MoviesIDList, inText).commit();
                                                      }else {
                                                          for(String restoreMovieID:restoredText){
                                                              if(restoreMovieID.equals(Long.toString(movies_result.id))) {
                                                                  inText.remove(restoreMovieID);
                                                                  editor.putStringSet(Utility.MoviesIDList, inText).commit();
                                                                  break;
                                                              }
                                                          }
                                                      }
                                                  }
                                              }
        );
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
