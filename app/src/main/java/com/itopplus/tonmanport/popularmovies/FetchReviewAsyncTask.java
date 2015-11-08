package com.itopplus.tonmanport.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.List;

import api.TheMoviesRepository;
import api.TheMoviesReviewModel;
import api.TheMoviesReviewResultList;

/**
 * Created by Tonman on 29/10/2558.
 */
public class FetchReviewAsyncTask extends AsyncTask<TheMoviesReviewModel,Void, TheMoviesReviewModel>{

    private Context mContext;
    private GridView gridView;
    public FetchReviewAsyncTask(Context context,GridView gridView){
        this.mContext = context;
        this.gridView = gridView;
    }

    @Override
    protected TheMoviesReviewModel doInBackground(TheMoviesReviewModel... theMoviesReviewModels) {
        TheMoviesRepository theMovieRepo = new TheMoviesRepository();
        try {
            TheMoviesReviewModel result = theMovieRepo.getReview(theMoviesReviewModels[0]);
            return result;
        }catch (JSONException ex){
            return null;
        }
    }

    private AdapterListViewReview adapterListViewTrailer;
    @Override
    protected void onPostExecute(TheMoviesReviewModel theMoviesReviewModel) {
        List<TheMoviesReviewResultList> results = theMoviesReviewModel.results;
        adapterListViewTrailer = new AdapterListViewReview(mContext,R.layout.listview_review,results);
        gridView.setAdapter(adapterListViewTrailer);
    }
}
