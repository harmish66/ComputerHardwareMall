package com.example.computerhardwaremall.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrderModel {

    @SerializedName("success")
    String success;

    @SerializedName("msg")
    String massage;

    @SerializedName("data")
    DataModel dataModel;

    public String getSuccess() {
        return success;
    }

    public String getMassage() {
        return massage;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public class DataModel {

        @SerializedName("data")
        List<DataM> data;

        public List<DataM> getData() {
            return data;
        }

        public class DataM {

            @SerializedName("datetime")
            String date;

            @SerializedName("id")
            String id;

            @SerializedName("totalprice")
            String price;

            @SerializedName("items")
            List<ComputerModel> mobile;


            public String getId() {
                return id;
            }

            public String getDate() {
                return date;
            }

            public String getPrice() {
                return price;
            }

            public List<ComputerModel> getMobile() {
                return mobile;
            }
        }
    }

}