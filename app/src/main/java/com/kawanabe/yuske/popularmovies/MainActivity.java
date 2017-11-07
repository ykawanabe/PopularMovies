package com.kawanabe.yuske.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kawanabe.yuske.popularmovies.models.Movie;
import com.kawanabe.yuske.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);


        getPopularMovies();
    }

    private void getPopularMovies() {
        URL url = NetworkUtils.buildUrl();

        OnTaskCompleted listner = new OnTaskCompleted() {
            @Override
            public void onFetchMoviesTaskCompleted(Movie[] movies) {
                for (Movie movie: movies){
                    Log.d("Tag", movie.title);
                }
            }
        };

        new FetchMovieAsyncTask(listner).execute(url);
    }

}
