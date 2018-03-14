package com.sas_apps.daskino.model.person;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("name")
    private String name;

    @SerializedName("biography")
    private String biography;

    @SerializedName("profile_path")
    private String profileImage;

    @SerializedName("imdb_id")
    private String imdbId;

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getImdbId() {
        return imdbId;
    }


    @Override
    public String toString() {
        return "\n\n" +
                "birthday='" + birthday + '\'' +
                "\n name='" + name + '\'' +
                "\n biography='" + biography + '\'' +
                "\n profileImage='" + profileImage + '\'' +
                "\n imdbId='" + imdbId + '\'';
    }
}
