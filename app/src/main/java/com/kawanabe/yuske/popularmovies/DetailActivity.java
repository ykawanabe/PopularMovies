package com.kawanabe.yuske.popularmovies;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kawanabe.yuske.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by yusuke on 11/10/17.
 */

public class DetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mReleaseDateTextView;
    private TextView mOverviewTextView;
    private TextView mRateTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mImageView = (ImageView) findViewById(R.id.iv_poster);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mOverviewTextView = (TextView) findViewById(R.id.tv_overview);
        mRateTextView = (TextView) findViewById(R.id.tv_vote);

        Intent intent = getIntent();

        if (intent != null) {
            Movie movie = intent.getParcelableExtra("movie");

            if (movie != null) {
                Log.d("Detail Activity", movie.title);
                setTitle(movie.title);
                Picasso.with(this).load(movie.imageURL()).into(mImageView);
                mOverviewTextView.setText(movie.overview);
                String releaseDateString = "Release Date: " + movie.releaseDate;
                mReleaseDateTextView.setText(releaseDateString);

                String voteString = String.format("Average Rating: %.1f", movie.voteAverage);
                mRateTextView.setText(voteString);
            }
        }
    }
}
