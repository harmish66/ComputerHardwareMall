package com.example.computerhardwaremall.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComputerResponseModel  {

    @SerializedName("computer")
    List<ComputerModel> computer;

    public List<ComputerModel> getComputer() {
        return computer;
    }

    public void setComputer(List<ComputerModel> computer) {
        this.computer = computer;
    }
}
