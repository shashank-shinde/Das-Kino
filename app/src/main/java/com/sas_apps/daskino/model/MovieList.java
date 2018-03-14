package com.sas_apps.daskino.model;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    public MovieList() {}

    @SerializedName("results")
    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }

}
