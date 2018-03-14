package com.sas_apps.daskino.adaptor;
/*
 * Created by Shashank Shinde.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sas_apps.daskino.R;
import com.sas_apps.daskino.model.Movie;
import com.sas_apps.daskino.utils.Utils;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridAdaptor extends RecyclerView.Adapter<GridAdaptor.GridViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private OnItemClicked onItemClicked;
    private boolean displayRatings;

    public GridAdaptor(Context context, List<Movie> movieList, OnItemClicked onItemClicked, boolean displayRatings) {
        this.context = context;
        this.movieList = movieList;
        this.onItemClicked = onItemClicked;
        this.displayRatings = displayRatings;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.textTitle.setText(movie.getTitle());
        if (displayRatings == false) {
            try {
                holder.textSubText.setText(String.format("Release: %s",Utils.getDate(movie.getReleaseDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finally {
                holder.textSubText.setText("");
            }
        } else {
            holder.textSubText.setText(String.format("Rating: %s", movie.getRating()));
        }
        holder.textTitle.setShadowLayer(30, 0, 0, Color.BLACK);
        Glide.with(context).load(Utils.THUMBNAIL_URL + movie.getPosterPath()).into(holder.imageThumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
//    private RecyclerViewClickListener recyclerViewClickListener;

    public void loadMoreResults(List<Movie> list) {
        this.movieList.addAll(list);
        notifyDataSetChanged();
    }


    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_subText)
        TextView textSubText;
        @BindView(R.id.image_thumbnail)
        ImageView imageThumbnail;

        GridViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onItemClicked(v, movieList.get(getLayoutPosition()).getId());
        }
    }

}
