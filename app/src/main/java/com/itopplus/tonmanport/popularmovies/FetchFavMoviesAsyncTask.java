package com.itopplus.tonmanport.popularmovies;

import android.os.AsyncTask;

import org.json.JSONException;

import api.TheMoviesModel;
import api.TheMoviesRepository;
import api.TheMoviesFavoriteListModel;

public class FetchFavMoviesAsyncTask extends AsyncTask<TheMoviesFavoriteListModel,Void, TheMoviesModel> {
    private final String TAG = "FetchMoviesAsyncTaskTAG";
    @Override
    protected TheMoviesModel doInBackground(TheMoviesFavoriteListModel... theMovies) {
        TheMoviesRepository theMovieRepo = new TheMoviesRepository();
        try {
            TheMoviesModel result = theMovieRepo.getFavoriteMove(theMovies[0]);
            return result;
        }catch (JSONException ex){
            return null;
        }
    }
}