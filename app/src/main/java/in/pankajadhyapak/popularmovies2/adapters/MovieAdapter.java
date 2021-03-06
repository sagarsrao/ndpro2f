package in.pankajadhyapak.popularmovies2.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.pankajadhyapak.popularmovies2.Constants;
import in.pankajadhyapak.popularmovies2.R;
import in.pankajadhyapak.popularmovies2.activites.MovieDetails;
import in.pankajadhyapak.popularmovies2.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static final String TAG = "MovieAdapter";
    private ArrayList<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(Context mContext, ArrayList<Movie> movies) {
        this.mMovies = movies;
        this.mContext = mContext;
        Log.e(TAG, "MovieAdapter: " + mMovies.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        holder.mTitle.setText(movie.getTitle());
        if (movie.getPosterPath() != null) {
            String posterUrl = Constants.TMDB_IMAGE_W500 + movie.getPosterPath();
            Picasso.with(mContext)
                    .load(posterUrl)
                    .placeholder(R.drawable.empty_photo)
                    .error(R.drawable.empty_photo)
                    .into(holder.mPoster);
        } else {
            holder.mPoster.setImageResource(R.drawable.empty_photo);
        }

        holder.mPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetails.class);
                intent.putExtra(Constants.MOVIE_DETAIL, movie);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((AppCompatActivity) mContext, (View) holder.singleMovie, mContext.getString(R.string.movie_poster));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mPoster;
        TextView mTitle;
        FrameLayout singleMovie;

        ViewHolder(View itemView) {
            super(itemView);
            mPoster = (ImageView) itemView.findViewById(R.id.moviePoster);
            mTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            singleMovie = (FrameLayout) itemView.findViewById(R.id.singleMovie);
        }
    }
}