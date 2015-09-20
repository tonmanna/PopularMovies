package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import lib.Utility;

public class TheMoviesRepository
{
    public TheMoviesModel getMovie(TheMoviesModel moviesAPI) throws JSONException {
        String result = Utility.HTTPGet(moviesAPI);
        JSONObject JsonObj = new JSONObject(result);
        TheMoviesModel returnResult = new TheMoviesModel();
        returnResult.page = Integer.parseInt(JsonObj.getString("page"));
        returnResult.total_pages = Integer.parseInt(JsonObj.getString("total_pages"));
        returnResult.total_results = Integer.parseInt(JsonObj.getString("total_results"));
        JSONArray resultsMoviesArray = JsonObj.getJSONArray("results");
        returnResult.results = new ArrayList<TheMoviesModelResultList>();
        for (int i = 0; i < resultsMoviesArray.length(); i++) {
            TheMoviesModelResultList movies = new TheMoviesModelResultList();
            JSONObject resultMovies = resultsMoviesArray.getJSONObject(i);
            movies.backdrop_path = "http://image.tmdb.org/t/p/w185" + resultMovies.getString("backdrop_path");
            movies.id = Long.parseLong(resultMovies.getString("id"));
            movies.title = resultMovies.getString("title");
            movies.original_title = resultMovies.getString("original_title");
            movies.overview = resultMovies.getString("overview");
            movies.poster_path = "http://image.tmdb.org/t/p/w185" + resultMovies.getString("poster_path");
            movies.poster_path_hires = "http://image.tmdb.org/t/p/w780" + resultMovies.getString("poster_path");

            movies.adult = resultMovies.getBoolean("adult");
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String dateString = resultMovies.getString("release_date");
                movies.release_date = dateFormat.parse(dateString);
            }catch (Exception ex){
                movies.release_date = null;
            }
            movies.video = resultMovies.getBoolean("video");
            movies.vote_average = resultMovies.getDouble("vote_average");
            movies.vote_count = resultMovies.getInt("vote_count");

            movies.popularity = resultMovies.getDouble("popularity");
            movies.original_language = resultMovies.getString("original_language");

            returnResult.results.add(movies);
        }
        if (result != null) {
            return returnResult;
        } else {
            return null;
        }
    }


}
