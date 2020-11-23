package com.example.retopokemon.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sprites implements Serializable {
    @SerializedName("front_default")
    private String img;

    public Sprites() {
    }

    public Sprites(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
