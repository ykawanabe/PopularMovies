package com.kawanabe.yuske.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kawanabe.yuske.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yusuke on 11/6/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    Movie[] mMovies;
    MovieAdapterOnClick mClickHandler;

    public MovieAdapter(MovieAdapterOnClick clickHander) {
        mClickHandler = clickHander;
    }


    public interface MovieAdapterOnClick {
        void onClick(Movie movie);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImageview;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageview = (ImageView) view.findViewById(R.id.iv_movie);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Movie selected = mMovies[index];
            mClickHandler.onClick(selected);
        }

        public void bind(Movie movie) {
            Picasso.with(mImageview.getContext()).load(movie.imageURL()).into(mImageview);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        int id = R.layout.movie_list_item;

        View view = inflator.inflate(id, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovies[position];
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) { return 0; }

        return mMovies.length;
    }

    public void setmMovies(Movie[] mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }
}
