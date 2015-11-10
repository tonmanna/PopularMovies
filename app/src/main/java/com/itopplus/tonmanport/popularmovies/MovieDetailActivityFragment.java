package com.itopplus.tonmanport.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.HashSet;
import java.util.Set;

import api.TheMoviesResultList;
import api.TheMoviesReviewModel;
import api.TheMoviesVideoModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import lib.Utility;

public class MovieDetailActivityFragment extends Fragment {
    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private int current_page = 1;
    private boolean bCheckFavorite = false;

    @Bind(R.id.detail_text_head)
    TextView tvHead1;

    @Bind(R.id.detail_text_head2)
    TextView tvHead2;

    @Bind(R.id.detail_text_content)
    TextView tvContent;

    @Bind(R.id.detail_image_content)
    ImageView imageView;

    @Bind(R.id.listview_review)
    GridView gridViewReview;

    @Bind(R.id.listview_video)
    GridView gridViewVideo;

    @Bind(R.id.favorite_checkbox)
    CheckBox cbFavorite;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this,view);
        Intent intent = getActivity().getIntent();
        final TheMoviesResultList movies_result;
        Bundle arguments = getArguments();
        if (arguments != null) {
            movies_result = arguments.getParcelable("movie");
        }else{
            movies_result = (TheMoviesResultList)intent.getSerializableExtra("movies_result");
        }

        if(movies_result!=null) {
            current_page = intent.getIntExtra("current_page", 1);

            tvHead1.setText(movies_result.title);

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());
            String textRow = "Vote :" + movies_result.vote_average + "/10 Release date : " + dateFormat.format(movies_result.release_date);
            tvHead2.setText(textRow);

            tvContent.setText(movies_result.overview);

            Glide.with(this)
                    .load(movies_result.poster_path_hires)
                    .centerCrop()
                    .into(imageView);
            FetchVideoAsyncTask fetchVideo = new FetchVideoAsyncTask(getActivity().getApplicationContext(), gridViewVideo);
            TheMoviesVideoModel apiVideo = new TheMoviesVideoModel();
            apiVideo.id = movies_result.id;
            fetchVideo.execute(apiVideo);

            FetchReviewAsyncTask fetchReview = new FetchReviewAsyncTask(getActivity().getApplicationContext(), gridViewReview);
            TheMoviesReviewModel apiReview = new TheMoviesReviewModel();
            apiReview.id = movies_result.id;
            fetchReview.execute(apiReview);


            final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final Set<String> restoredText = sharedPrefs.getStringSet(Utility.MoviesIDList, new HashSet<String>());
            for (String restoreMovieID : restoredText) {
                if (restoreMovieID.equals(Long.toString(movies_result.id))) {
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
                                                          if (isChecked) {
                                                              inText.add(Long.toString(movies_result.id));
                                                              editor.putStringSet(Utility.MoviesIDList, inText).commit();
                                                          } else {
                                                              for (String restoreMovieID : restoredText) {
                                                                  if (restoreMovieID.equals(Long.toString(movies_result.id))) {
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
        return view;
    }
}
