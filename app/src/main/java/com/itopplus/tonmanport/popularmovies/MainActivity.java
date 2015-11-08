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

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import api.TheMoviesModel;
import api.TheMoviesReviewModel;
import api.TheMoviesVideoModel;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "PopularMoviesTAG";
    private boolean bToggle = true;
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        FetchMoviesAsyncTask fetchMovies = new FetchMoviesAsyncTask();
        TheMoviesModel api = new TheMoviesModel();
        if (bToggle)
            api.mode = "popularity";
        else
            api.mode = "vote_average";

        api.page = currentPage;

        fetchMovies.execute(api);
        try {
            result = fetchMovies.get();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggle) {
            bToggle = !bToggle;
            if(bToggle)
                item.setTitle(R.string.highrate_text);
            else
                item.setTitle(R.string.popularity_text);

            currentPage = 1;
            RefreshData();
        }else if(id == R.id.action_nextpage){
            currentPage = currentPage+1;
            RefreshData();
        }else if(id == R.id.action_prevpage){
            if(currentPage!=1) {
                currentPage = currentPage - 1;
                RefreshData();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("bToggle", bToggle);
        savedInstanceState.putInt("currentPage", currentPage);
        super.onSaveInstanceState(savedInstanceState);
    }
}
