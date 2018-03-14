package com.sas_apps.daskino.model.movieDetails;

/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

public class MovieCast {


    @SerializedName("character")
    private String character;

    @SerializedName("name")
    private String name;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("profile_path")
    private String profilePhoto;

    @SerializedName("gender")
    private String gender;

    @SerializedName("id")
    private String id;


    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString() {
        return "\n\n" +
                "character='" + character + '\'' +
                "\n name='" + name + '\'' +
                "\n credit_id='" + credit_id + '\'' +
                "\n profilePhoto='" + profilePhoto + '\'' +
                "\n gender='" + gender + '\'' +
                "\n id='" + id + '\'';
    }
}
