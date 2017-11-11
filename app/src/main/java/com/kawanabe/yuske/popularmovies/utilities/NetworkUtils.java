package com.kawanabe.yuske.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by yusuke on 11/3/17.
 */

public class NetworkUtils {

    final static String BASE_URL = "http://api.themoviedb.org/3/movie";

    final static String PARAM_API_KEY = "api_key";
    final static String API_KEY = "";


    public static URL buildUrl(String path) {
    Uri buildUri = Uri.parse(BASE_URL).buildUpon()
            .appendPath(path)
            .appendQueryParameter(PARAM_API_KEY, API_KEY)
            .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("tag", url.toString());
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
