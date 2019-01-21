package com.example.elibrary.entity;

import com.google.gson.annotations.SerializedName;

public class VolumeInfo {

    private String title;
    private String[] authors;

    @SerializedName("imageLinks")
    private VolumeImage image;

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public VolumeImage getImage() {
        return image;
    }

}
