package com.example.computerhardwaremall.Models;

import com.google.gson.annotations.SerializedName;

public class Categories {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
