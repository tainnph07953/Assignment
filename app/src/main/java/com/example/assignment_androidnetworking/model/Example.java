
package com.example.assignment_androidnetworking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("photos")
    @Expose
    private PhotosFavorite photosFavorite;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotosFavorite getPhotosFavorite() {
        return photosFavorite;
    }

    public void setPhotosFavorite(PhotosFavorite photosFavorite) {
        this.photosFavorite = photosFavorite;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
