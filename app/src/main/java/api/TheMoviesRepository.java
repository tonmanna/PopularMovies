package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import lib.Utility;

public class TheMoviesRepository
{
    public TheMoviesModel getMovie(TheMoviesModel moviesAPI) throws JSONException {
        try {
            String result = Utility.HTTPGet(moviesAPI);
            JSONObject JsonObj = new JSONObject(result);
            TheMoviesModel returnResult = new TheMoviesModel();
            returnResult.page = JsonObj.getInt("page");
            returnResult.total_pages = JsonObj.getInt("total_pages");
            returnResult.total_results = JsonObj.getInt("total_results");
            JSONArray resultsMoviesArray = JsonObj.getJSONArray("results");
            returnResult.results = new ArrayList<TheMoviesResultList>();
            for (int i = 0; i < resultsMoviesArray.length(); i++) {
                TheMoviesResultList movies = new TheMoviesResultList();
                JSONObject resultMovies = resultsMoviesArray.getJSONObject(i);
                movies.backdrop_path = "http://image.tmdb.org/t/p/w185" + resultMovies.getString("backdrop_path");
                movies.id = resultMovies.getLong("id");
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
                } catch (Exception ex) {
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
        }catch (IOException ex) {
            return null;
        }
    }

    public TheMoviesVideoModel getVideo(TheMoviesVideoModel moviesVideoAPI) throws JSONException {
        try {
            String result = Utility.HTTPGet(moviesVideoAPI);
            JSONObject JsonObj = new JSONObject(result);
            TheMoviesVideoModel returnResult = new TheMoviesVideoModel();
            returnResult.id = JsonObj.getLong("id");
            JSONArray resultsMoviesVideoArray = JsonObj.getJSONArray("results");
            returnResult.results = new ArrayList<TheMoviesVideoResultList>();
            for (int i = 0; i < resultsMoviesVideoArray.length(); i++) {
                TheMoviesVideoResultList moviesVideo = new TheMoviesVideoResultList();
                JSONObject resultMovies = resultsMoviesVideoArray.getJSONObject(i);
                moviesVideo.id = resultMovies.getString("id");
                moviesVideo.iso_639_1 = resultMovies.getString("iso_639_1");
                moviesVideo.key = resultMovies.getString("key");
                moviesVideo.name = resultMovies.getString("name");
                moviesVideo.site = resultMovies.getString("site");
                moviesVideo.size = resultMovies.getInt("size");
                moviesVideo.type = resultMovies.getString("type");
                returnResult.results.add(moviesVideo);
            }
            if (result != null) {
                return returnResult;
            } else {
                return null;
            }
        } catch (IOException ex) {
            return null;
        }
    }

    public TheMoviesReviewModel getReview(TheMoviesReviewModel moviesReviewAPI) throws JSONException {
        try {
            String result = Utility.HTTPGet(moviesReviewAPI);
            JSONObject JsonObj = new JSONObject(result);
            TheMoviesReviewModel returnResult = new TheMoviesReviewModel();
            returnResult.id = JsonObj.getLong("id");
            JSONArray resultsMoviesReviewArray = JsonObj.getJSONArray("results");
            returnResult.results = new ArrayList<TheMoviesReviewResultList>();
            for (int i = 0; i < resultsMoviesReviewArray.length(); i++) {
                TheMoviesReviewResultList moviesReview = new TheMoviesReviewResultList();
                JSONObject resultMovies = resultsMoviesReviewArray.getJSONObject(i);
                moviesReview.id = resultMovies.getString("id");
                moviesReview.author = resultMovies.getString("author");
                moviesReview.content = resultMovies.getString("content");
                moviesReview.url = resultMovies.getString("url");
                returnResult.results.add(moviesReview);
            }
            if (result != null) {
                return returnResult;
            } else {
                return null;
            }
        } catch (IOException ex) {
            return null;
        }
    }


    public TheMoviesModel getFavoriteMove(TheMoviesFavoriteListModel moviesFavoriteAPI) throws JSONException {
        try {
            TheMoviesModel returnResult = new TheMoviesModel();
            returnResult.page = 1;
            returnResult.total_pages = 1;
            returnResult.total_results = moviesFavoriteAPI.moviesIDList.size();
            returnResult.results = new ArrayList<TheMoviesResultList>();

            for(String strMovieID : moviesFavoriteAPI.moviesIDList) {
                //TODO : GET result then put to List<Object>
                moviesFavoriteAPI.id = Long.valueOf(strMovieID);
                String result = Utility.HTTPGet(moviesFavoriteAPI);
                JSONObject JsonObj = new JSONObject(result);
                TheMoviesResultList movies = new TheMoviesResultList();

                movies.backdrop_path = "http://image.tmdb.org/t/p/w185" + JsonObj.getString("backdrop_path");
                movies.id = JsonObj.getLong("id");
                movies.title = JsonObj.getString("title");
                movies.original_title = JsonObj.getString("original_title");
                movies.overview = JsonObj.getString("overview");
                movies.poster_path = "http://image.tmdb.org/t/p/w185" + JsonObj.getString("poster_path");
                movies.poster_path_hires = "http://image.tmdb.org/t/p/w780" + JsonObj.getString("poster_path");
                movies.adult = JsonObj.getBoolean("adult");
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String dateString = JsonObj.getString("release_date");
                    movies.release_date = dateFormat.parse(dateString);
                } catch (Exception ex) {
                    movies.release_date = null;
                }

                movies.video = JsonObj.getBoolean("video");
                movies.vote_average = JsonObj.getDouble("vote_average");
                movies.vote_count = JsonObj.getInt("vote_count");
                movies.popularity = JsonObj.getDouble("popularity");
                movies.original_language = JsonObj.getString("original_language");
                returnResult.results.add(movies);
            }
            if (returnResult != null) {
                return returnResult;
            } else {
                return null;
            }
        }catch (IOException ex) {
            return null;
        }
    }
}
