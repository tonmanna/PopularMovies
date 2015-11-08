package lib;

import android.util.Log;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.facebook.stetho.Stetho;

/**
 * Created by Tonman on 26/8/2558.
 */
public class Utility {

    private Utility(){
    }
    public static String HTTPGetManual(IHTTPRequestParam params){
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String resultString = null;

        try {
            URL url = new URL(params.getURL().toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                resultString = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                resultString = null;
            }else {
                resultString = buffer.toString();
            }
        } catch (IOException e) {
            Log.e("UTILITYHelperTAG", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            resultString = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("UTILITYHelperTAG", "Error closing stream", e);
                }
            }
        }
        return resultString;
    }
    public static String HTTPGet(IHTTPRequestParam params) throws IOException {
        OkHttpClient  client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        Log.w("TAG:",params.getURL().toString());
        Request request = new Request.Builder()
                .url(params.getURL().toString())
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
