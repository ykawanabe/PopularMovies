package com.kawanabe.yuske.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yusuke on 11/5/17.
 */

public class Movie {
    public String title;
    public String posterPath;

    private final String KEY_TITLE = "title";
    private final String KEY_POSTER = "poster_path";

    public String imageURL() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    public Movie(JSONObject json) {
        try {
            title = json.getString(KEY_TITLE);
            posterPath = json.getString(KEY_POSTER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
