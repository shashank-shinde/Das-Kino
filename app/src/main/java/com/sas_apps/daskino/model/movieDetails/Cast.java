package com.sas_apps.daskino.model.movieDetails;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast {

    @SerializedName("cast")
    private List<MovieCast> castList;

    public List<MovieCast> getCastList() {
        return castList;
    }
}
