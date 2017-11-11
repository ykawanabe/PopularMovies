package com.kawanabe.yuske.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yusuke on 11/5/17.
 */

public class Movie implements Parcelable {
    public String title;
    public String posterPath;
    public String overview;
    public String releaseDate;
    public Double voteAverage;

    public String imageURL() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    public Movie(JSONObject json) {
        try {
            title = json.getString("title");
            posterPath = json.getString("poster_path");
            overview = json.getString("overview");
            releaseDate = json.getString("release_date");
            voteAverage = json.getDouble("vote_average");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }
}
