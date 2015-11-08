package api;

import android.net.Uri;

import java.util.List;

import lib.IHTTPRequestParam;

/**
 * Created by Tonman on 29/10/2558.
 */
public class TheMoviesVideoModel implements IHTTPRequestParam {

    public String api = "14dd997f3bce981d5cc1118ace4bf690";
    private final String API_KEY = "api_key";
    public final String BASE_URL = "api.themoviedb.org";
    public final String API_VERSION = "3";
    public final String SERVICE_PATH = "movie";
    public long id;
    public List<TheMoviesVideoResultList> results;

    @Override
    public Uri getURL() {
        Uri.Builder builder = new Uri.Builder();
        Uri buildUri = builder.scheme("http")
                .authority(BASE_URL)
                .appendPath(API_VERSION)
                .appendPath(SERVICE_PATH)
                .appendPath(String.valueOf(id))
                .appendPath("videos")
                .appendQueryParameter(API_KEY, api).build();
        return buildUri;

    }
}
