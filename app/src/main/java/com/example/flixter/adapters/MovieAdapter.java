package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies)
    {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);



        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView tvHotOrNot;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvHotOrNot = itemView.findViewById(R.id.hotOrNot);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie)
        {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvHotOrNot.setVisibility(View.INVISIBLE);

            String imageUrl;
            // if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getBackDropPath();
            }
            else
            {
                imageUrl = movie.getPosterPath();
            }

            if(movie.getRating() > 7)
            {
                container.setBackgroundColor(Color.rgb(230,127,80));
                tvHotOrNot.setVisibility(View.VISIBLE);
                tvTitle.setTextColor(Color.BLACK);
                tvOverview.setTextColor(Color.BLACK);
            }
            else
            {
                container.setBackgroundColor(Color.rgb(255,255,255));
                tvTitle.setTextColor(Color.BLACK);
                tvOverview.setTextColor(Color.BLACK);
            }

            //load the drawable while the image loads
            Glide.with(context)
                    .load(imageUrl)
                    .into(ivPoster);

            //1. Register click listener on the whole row
            //2. Navigate to a new activity on tap

            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
//                    i.putExtra("movie",)
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
