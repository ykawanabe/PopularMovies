package com.kawanabe.yuske.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import com.kawanabe.yuske.popularmovies.models.Movie;
import com.kawanabe.yuske.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by yusuke on 11/5/17.
 */


public class FetchMovieAsyncTask extends AsyncTask<URL, Void, Movie[]> {
    OnTaskCompleted mListner;

    public FetchMovieAsyncTask(OnTaskCompleted listner) {
        mListner = listner;
    }

    @Override
    protected Movie[] doInBackground(URL... params) {
        URL url = params[0];
        String resultString = null;

        try {
            resultString = NetworkUtils.getResponseFromHttpUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Movie[] result = getMoviesFromJSONString(resultString);
        return result;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        mListner.onFetchMoviesTaskCompleted(movies);
    }

    private Movie[] getMoviesFromJSONString(String jsonString) {
        final String TAG_RESULTS = "results";
        Movie[] movies = new Movie[0];
        if (jsonString == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_RESULTS);
            movies = new Movie[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Movie movie = new Movie(object);
                movies[i] = movie;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}

interface OnTaskCompleted {
    void onFetchMoviesTaskCompleted(Movie[] movies);
}
