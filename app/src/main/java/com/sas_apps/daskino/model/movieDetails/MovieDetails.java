package com.sas_apps.daskino.model.movieDetails;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    @SerializedName("id")
    private String id;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;


    @SerializedName("vote_average")
    private String ratings;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;


    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;


    @SerializedName("runtime")
    private String runtime;

    @SerializedName("budget")
    private String budget;

    @SerializedName("revenue")
    private String boxOffice;

    @SerializedName("imdb_id")
    private String imdbId;


    public String getId() {
        return id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getRatings() {
        return ratings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getBudget() {
        return budget;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public String getImdbId() {
        return imdbId;
    }

    @Override
    public String toString() {
        return "\n\n" +
                "id='" + id + '\'' +
                "\n backdropPath='" + backdropPath + '\'' +
                "\n posterPath='" + posterPath + '\'' +
                "\n title='" + title + '\'' +
                "\n ratings='" + ratings + '\'' +
                "\n releaseDate='" + releaseDate + '\'' +
                "\n overview='" + overview + '\'' +
                "\n originalTitle='" + originalTitle + '\'' +
                "\n originalLanguage='" + originalLanguage + '\'' +
                "\n runtime='" + runtime + '\'' +
                "\n budget='" + budget + '\'' +
                "\n boxOffice='" + boxOffice + '\'' +
                "\n imdbId='" + imdbId + '\'';
    }
}
