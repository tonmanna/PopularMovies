package com.itopplus.tonmanport.popularmovies;

import android.os.AsyncTask;
import org.json.JSONException;

import api.TheMoviesRepository;
import api.TheMoviesModel;

public class FetchMoviesAsyncTask extends AsyncTask<TheMoviesModel,Void, TheMoviesModel> {
    private final String TAG = "FetchMoviesAsyncTaskTAG";
    @Override
    protected TheMoviesModel doInBackground(TheMoviesModel... theMovies) {
        TheMoviesRepository theMovieRepo = new TheMoviesRepository();
        try {
            TheMoviesModel result = theMovieRepo.getMovie(theMovies[0]);
            return result;
        }catch (JSONException ex){
            return null;
        }
    }
}