package com.example.computerhardwaremall.Models;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("success")
    String success;

    @SerializedName("massage")
    String massage;

    public String getSuccess() {
        return success;
    }

    public String getMassage() {
        return massage;
    }
}
