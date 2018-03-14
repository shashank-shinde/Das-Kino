package com.sas_apps.daskino.model;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("backdrop_path")
    private String backgroundImage;

    @SerializedName("original_language")
    private String originalLanguage;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }



    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getPosterPath() {
        return posterPath;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "\n\n" +
                "id='" + id + '\'' +
                "\n title='" + title + '\'' +
                "\n posterPath='" + posterPath + '\'' +
                "\n rating='" + rating + '\'' +
                "\n overview='" + overview + '\'' +
                "\n releaseDate='" + releaseDate + '\'';
    }
}
