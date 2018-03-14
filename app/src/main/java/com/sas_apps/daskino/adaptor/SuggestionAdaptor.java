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
import de.hdodenhof.circleimageview.CircleImageView;

public class SuggestionAdaptor extends RecyclerView.Adapter<SuggestionAdaptor.SuggestionListHolder> {

    private Context context;
    private List<Movie> movieList;
    private OnSuggestionClicked onSuggestionClicked;

    public SuggestionAdaptor(Context context, List<Movie> movieList, OnSuggestionClicked onSuggestionClicked) {
        this.context = context;
        this.movieList = movieList;
        this.onSuggestionClicked = onSuggestionClicked;
    }

    @Override
    public SuggestionListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_people_also_like, parent, false);
        return new SuggestionListHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestionListHolder holder, int position) {
        if (movieList.get(position).getPosterPath() != null) {
            holder.textName.setText(movieList.get(position).getTitle());
            Glide.with(context)
                    .load(Utils.THUMBNAIL_URL + movieList.get(position).getPosterPath())
                    .into(holder.imageMoviePoster);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class SuggestionListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_suggestion)
        ImageView imageMoviePoster;
        @BindView(R.id.text_suggestion)
        TextView textName;

        SuggestionListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSuggestionClicked.onSuggestionClicked(v, movieList.get(getLayoutPosition()).getId());
        }
    }
}
