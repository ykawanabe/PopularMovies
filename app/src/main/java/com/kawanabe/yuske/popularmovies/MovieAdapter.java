package com.kawanabe.yuske.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kawanabe.yuske.popularmovies.models.Movie;

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
        private final TextView mTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Movie selected = mMovies[index];
            mClickHandler.onClick(selected);
        }

        public void bind(Movie movie) {
            mTextView.setText(movie.title);
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
        return mMovies.length;
    }
}
