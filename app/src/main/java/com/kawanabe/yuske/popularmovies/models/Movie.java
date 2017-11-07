package com.kawanabe.yuske.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yusuke on 11/5/17.
 */

public class Movie {
    public String title;

    private final String KEY_TITLE = "title";

    public Movie(JSONObject json) {
        try {
            title = json.getString(KEY_TITLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
