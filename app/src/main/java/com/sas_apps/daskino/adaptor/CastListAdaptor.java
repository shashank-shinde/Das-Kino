package com.sas_apps.daskino.adaptor;
/*
 * Created by Shashank Shinde.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sas_apps.daskino.R;
import com.sas_apps.daskino.model.movieDetails.MovieCast;
import com.sas_apps.daskino.utils.Utils;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CastListAdaptor extends RecyclerView.Adapter<CastListAdaptor.CastListHolder> {

    private Context context;
    private List<MovieCast> castList;
    private OnItemClicked onItemClicked;

    public CastListAdaptor(Context context, List<MovieCast> castList, OnItemClicked onItemClicked) {
        this.context = context;
        this.castList = castList;
        this.onItemClicked = onItemClicked;
    }

    @Override
    public CastListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cast, parent, false);
        return new CastListHolder(view);
    }

    @Override
    public void onBindViewHolder(CastListHolder holder, int position) {
        MovieCast movieCast = castList.get(position);
        if (movieCast.getProfilePhoto()!=null){
            holder.textName.setText(movieCast.getName());
            if (movieCast.getCharacter()!=null){
                holder.textRole.setText(MessageFormat.format("{0}{1})", '(',
                        movieCast.getCharacter()));
            }
            Glide.with(context)
                    .load(Utils.THUMBNAIL_URL + movieCast.getProfilePhoto())
                    .into(holder.imageCast);
        }
//        else {
//            holder.imageCast.setImageResource(R.drawable.ic_person);
//        }
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_cast)
        CircleImageView imageCast;
        @BindView(R.id.text_cast)
        TextView textName;
        @BindView(R.id.text_role)
        TextView textRole;

        CastListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onItemClicked(v,castList.get(getLayoutPosition()).getId());
            Toast.makeText(context, castList.get(getLayoutPosition()).getId(), Toast.LENGTH_SHORT).show();
        }
    }
}
