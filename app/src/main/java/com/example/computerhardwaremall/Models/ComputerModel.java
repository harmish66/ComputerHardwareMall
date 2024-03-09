package com.example.computerhardwaremall.Models;

import com.google.gson.annotations.SerializedName;

public class ComputerModel {

    @SerializedName("id")
    String id;

    @SerializedName("cat_id")
    String cat_id;

    @SerializedName("modelname")
    String modelname;

    @SerializedName("price")
    String price;

    @SerializedName("manufacturer")
    String manufacturer;

    @SerializedName("brand")
    String brand;

    @SerializedName("color")
    String color;

    @SerializedName("warranty")
    String warranty;

    @SerializedName("description")
    String description;

    @SerializedName("photo")
    String photo;

    public ComputerModel() {

    }

    public ComputerModel(String id, String modelname, String brand, String price, String photo) {
        this.id = id;
        this.modelname = modelname;
        this.brand = brand;
        this.price = price;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getwarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getDes() {
        return description;
    }

    public void setDes(String des) {
        this.description = des;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}


