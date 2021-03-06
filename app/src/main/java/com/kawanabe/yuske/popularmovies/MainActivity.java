package com.kawanabe.yuske.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.kawanabe.yuske.popularmovies.models.Movie;
import com.kawanabe.yuske.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClick {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private DisplayType mCurrentType;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentType = DisplayType.popular;
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);

        getPopularMovies();
    }

    private void getPopularMovies() {
        DisplayType type = mCurrentType;

        setTitle(type.getString());
        URL url = NetworkUtils.buildUrl(type.path());

        OnTaskCompleted listner = new OnTaskCompleted() {
            @Override
            public void onFetchMoviesTaskCompleted(Movie[] movies) {
                mProgressBar.setVisibility(View.INVISIBLE);

                if (movies == null || movies.length == 0) {
                    showErrorMessage();
                } else {
                    mMovieAdapter.setmMovies(movies);
                }
            }
        };

        mProgressBar.setVisibility(View.VISIBLE);
        new FetchMovieAsyncTask(listner).execute(url);
    }

    private int numberOfColumns() {
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int widthDivider = 400;
        int nColumns = display.widthPixels / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
    private void showErrorMessage() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Error");
        alert.setMessage("No movie data is available.");
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Retry",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPopularMovies();
                        dialog.dismiss();
                    }
                });
        alert.show();
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("tag", "ok");
        if (id == R.id.action_order) {
            // update order
            updateSortOrder();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void updateSortOrder() {
        mCurrentType = mCurrentType.next();
        getPopularMovies();
    }

    private enum DisplayType {
        popular("Popular movies"),
        topRated("Top rated movies");

        final static String POPULAR_PATH = "popular";
        final static String TOP_RATED_PATH = "top_rated";

        private final String text;

        private DisplayType(final String text) {
            this.text = text;
        }

        public String getString() {
            return this.text;
        }

        public DisplayType next() {
            switch (this) {
                case popular:
                    return DisplayType.topRated;
                case topRated:
                    return DisplayType.popular;
            }

            return DisplayType.topRated;
        }

        public String path() {
            switch (this) {
                case popular:
                    return POPULAR_PATH;
                case topRated:
                    return TOP_RATED_PATH;
            }

            return null;
        }
    }
}
