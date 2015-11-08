package api;

import android.net.Uri;

import java.util.List;

import lib.IHTTPRequestParam;

/**
 * Created by Tonman on 29/10/2558.
 */
public class TheMoviesReviewModel implements IHTTPRequestParam {

    // params
    private final String API_KEY = "api_key";
    private final String PAGE = "page";
    public final String BASE_URL = "api.themoviedb.org";
    public final String API_VERSION = "3";
    public final String SERVICE_PATH = "movie";

    public String api = "14dd997f3bce981d5cc1118ace4bf690";

    public long id;
    public int page = 1;
    public String language;

    public String total_pages;
    public String total_results;

    public List<TheMoviesReviewResultList> results;

    @Override
    public Uri getURL() {
        Uri.Builder builder = new Uri.Builder();
        Uri buildUri = builder.scheme("http")
                .authority(BASE_URL)
                .appendPath(API_VERSION)
                .appendPath(SERVICE_PATH)
                .appendPath(String.valueOf(id))
                .appendPath("reviews")
                .appendQueryParameter(API_KEY, api)
                .appendQueryParameter(PAGE, String.valueOf(page))
                .build();
        return buildUri;
    }
}
