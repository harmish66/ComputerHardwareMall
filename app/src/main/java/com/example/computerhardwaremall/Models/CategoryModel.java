package com.example.computerhardwaremall.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.jar.Attributes;

public class CategoryModel {

    @SerializedName("category")
    List<Categories> categories;

    public List<Categories> getCategories() {
        return categories;
    }
}
