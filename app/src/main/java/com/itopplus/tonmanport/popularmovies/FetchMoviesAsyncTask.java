package com.itopplus.tonmanport.popularmovies;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Tonman on 26/8/2558.
 */
public class FetchMoviesAsyncTask extends AsyncTask<String,Void,ArrayList<theMovieDBItems>> {
    private final String TAG = "FetchMoviesAsyncTaskTAG";

    @Override
    protected ArrayList<theMovieDBItems> doInBackground(String... params) {
        return  null;
    }
}
