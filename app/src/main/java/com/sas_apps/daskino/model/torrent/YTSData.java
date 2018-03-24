package com.sas_apps.daskino.model.torrent;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YTSData {

    @SerializedName("movies")
    private List<YTSMovies> ytsMoviesList;


    @SerializedName("movie_count")
    private String movieCount;

    public String getMovieCount() {
        return movieCount;
    }

    public List<YTSMovies> getYtsMoviesList() {
        return ytsMoviesList;
    }
}
