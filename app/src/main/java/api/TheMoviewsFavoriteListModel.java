package api;

import android.net.Uri;

import java.util.List;
import java.util.Set;

import lib.IHTTPRequestParam;
import lib.Utility;

/**
 * Created by Tonman on 9/11/2558.
 */
public class TheMoviewsFavoriteListModel implements IHTTPRequestParam{
    public String api = Utility.API_KEY;
    private final String API_KEY = "api_key";
    public final String BASE_URL = "api.themoviedb.org";
    public final String API_VERSION = "3";
    public final String SERVICE_PATH = "movie";

    public long id;
    public Set<String> moviesIDList;

    // Result Parameter
    public List<TheMoviesResultList> results;

    @Override
    public Uri getURL() {
        Uri.Builder builder = new Uri.Builder();
        Uri buildUri = builder.scheme("http")
                .authority(BASE_URL)
                .appendPath(API_VERSION)
                .appendPath(SERVICE_PATH)
                .appendPath(String.valueOf(id))
                .appendQueryParameter(API_KEY, api).build();
        return buildUri;
    }
}
