package api;

import android.net.Uri;

import java.util.List;

import lib.IHTTPRequestParam;

/**
 * Created by Tonman on 26/8/2558.
 */
public class TheMoviesModel implements IHTTPRequestParam{

    // URL Parameter
    public String api = "14dd997f3bce981d5cc1118ace4bf690";
    public final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
    private final String API_KEY = "api_key";
    private final String SORT_BY_AND_MODE = "sort_by";
    private final String PAGE = "page";

    // Request Parameter
    public String mode = "popularity"; // Default sort by popularity desc
    public String sort = "desc"; // Default sort by popularity desc
    public int page = 1;

    // Result Parameter
    public int total_pages;
    public int total_results;
    public List<TheMoviesResultList> results;

    @Override
    public Uri getURL() {
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY_AND_MODE, mode+"."+sort)
                .appendQueryParameter(PAGE, String.valueOf(page))
                .appendQueryParameter(API_KEY, api)
                .build();
        return buildUri;
    }
}
