package com.itopplus.tonmanport.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import api.TheMoviesModel;
import api.TheMoviesFavoriteListModel;
import lib.Utility;

public class MainActivity extends AppCompatActivity {
    public enum DisplayMode{
        Popular,
        Vote_Average,
        Favorite
    }

    public final String TAG = "PopularMoviesTAG";
    private boolean bToggle = true;
    private DisplayMode mDisplayMode = DisplayMode.Popular;
    private int currentPage = 1;
    TheMoviesModel result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            bToggle = savedInstanceState.getBoolean("bToggle");
            currentPage = savedInstanceState.getInt("currentPage");
        }else{

        }
        setContentView(R.layout.activity_main);
        RefreshData();
    }

    public void RefreshData() {
        try {
            if(mDisplayMode == DisplayMode.Popular || mDisplayMode == DisplayMode.Vote_Average) {
                TheMoviesModel api = new TheMoviesModel();
                api.mode = mDisplayMode == DisplayMode.Popular ?  "popularity" : "vote_average";
                api.page = currentPage;
                FetchMoviesAsyncTask fetchMovies = new FetchMoviesAsyncTask();
                fetchMovies.execute(api);
                result = fetchMovies.get();
            }else{
                FetchFavMoviesAsyncTask fetchFavMovies = new FetchFavMoviesAsyncTask();
                TheMoviesFavoriteListModel api = new TheMoviesFavoriteListModel();

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                Set<String> restoredText = sharedPrefs.getStringSet(Utility.MoviesIDList, new HashSet<String>());
                Log.d("TAG", String.valueOf(restoredText.size()));
                if(restoredText.size()>0) {
                    api.moviesIDList = restoredText;
                    fetchFavMovies.execute(api);
                    result = fetchFavMovies.get();
                }else{
                    Toast.makeText(this,"No found any favorite movies.",Toast.LENGTH_SHORT);
                    return;
                }

            }

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(this, R.layout.grid_item, result.results));
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MovieDetailActivity.class);
                    intent.putExtra("movies_result", result.results.get(position));
                    intent.putExtra("current_page", currentPage);
                    startActivity(intent);
                }
            });
        }catch (Exception ex){
            Toast toast = Toast.makeText(this, "Can't fetch data this time. Please try again later.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            mDisplayMode = DisplayMode.Popular;
            currentPage = 1;
        }else if (id == R.id.action_highrate) {
            mDisplayMode = DisplayMode.Vote_Average;
            currentPage = 1;
        }
        else if(id == R.id.action_favorite){
            mDisplayMode = DisplayMode.Favorite;
            currentPage = 1;
        }
        else if(id == R.id.action_nextpage){
            currentPage = currentPage+1;
        }else if(id == R.id.action_prevpage){
            if(currentPage!=1) {
                currentPage = currentPage - 1;
            }
        }
        RefreshData();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("bToggle", bToggle);
        savedInstanceState.putInt("currentPage", currentPage);
        super.onSaveInstanceState(savedInstanceState);
    }
}
