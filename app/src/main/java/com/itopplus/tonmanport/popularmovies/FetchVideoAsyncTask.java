package com.itopplus.tonmanport.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.List;

import api.TheMoviesModel;
import api.TheMoviesRepository;
import api.TheMoviesReviewResultList;
import api.TheMoviesVideoModel;
import api.TheMoviesVideoResultList;

/**
 * Created by Tonman on 29/10/2558.
 */
public class FetchVideoAsyncTask extends AsyncTask<TheMoviesVideoModel,Void,TheMoviesVideoModel>{

    private Context mContext;
    private GridView gridView;
    private AdapterListViewTrailer adapterListViewTrailer;

    public FetchVideoAsyncTask(Context context,GridView gridView){
        this.mContext = context;
        this.gridView = gridView;
    }

    @Override
    protected TheMoviesVideoModel doInBackground(TheMoviesVideoModel... theMoviesVideoModels) {
        TheMoviesRepository theMovieRepo = new TheMoviesRepository();
        try {
            TheMoviesVideoModel result = theMovieRepo.getVideo(theMoviesVideoModels[0]);
            return result;
        }catch (JSONException ex){
            return null;
        }
    }

    @Override
    protected void onPostExecute(TheMoviesVideoModel theMoviesVideoModel) {
        List<TheMoviesVideoResultList> results = theMoviesVideoModel.results;
        adapterListViewTrailer = new AdapterListViewTrailer(mContext,R.layout.listview_video,results);
        gridView.setAdapter(adapterListViewTrailer);
    }
}
