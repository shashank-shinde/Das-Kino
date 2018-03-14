package com.sas_apps.daskino.adaptor;
/*
 * Created by Shashank Shinde.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdaptor extends RecyclerView.Adapter<ListAdaptor.ListViewHolder> {


    private Context context;
    private List<Movie> movieList;
    private OnItemClicked onItemClicked;

    public ListAdaptor(Context context, List<Movie> movieList, OnItemClicked onItemClicked) {
        this.context = context;
        this.movieList = movieList;
        this.onItemClicked = onItemClicked;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list2, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.textTitle.setText(movie.getTitle());
        holder.textOverview.setText("Overview: "+movie.getOverview());
        holder.textRatings.setText("Rating: "+movie.getRating());
        holder.textReleseDate.setText("Release: "+movie.getReleaseDate());
        Glide.with(context).load(Utils.THUMBNAIL_URL + movie.getPosterPath()).into(holder.imageThumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void loadMoreResults(List<Movie> movieList) {
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_listTitle)
        TextView textTitle;
        @BindView(R.id.text_listReleseDate)
        TextView textReleseDate;
        @BindView(R.id.text_listRatings)
        TextView textRatings;
        @BindView(R.id.text_listOverview)
        TextView textOverview;
        @BindView(R.id.image_list)
        ImageView imageThumbnail;

        ListViewHolder(View view) {
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
