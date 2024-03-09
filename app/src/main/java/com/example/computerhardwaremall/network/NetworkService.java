package com.example.computerhardwaremall.network;

import com.example.computerhardwaremall.Models.CategoryModel;
import com.example.computerhardwaremall.Models.ComputerResponseModel;
import com.example.computerhardwaremall.Models.LoginModel;
import com.example.computerhardwaremall.Models.OrderModel;
import com.example.computerhardwaremall.Models.RegisterModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService{

    @GET("category.php")
    Call<CategoryModel> getCategory();

    @FormUrlEncoded
    @POST("computer.php")
    Call<ComputerResponseModel> getComputer(@Field("catid") String catid);

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterModel> registerUser(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> loginUser(@Field("email") String email,  @Field("pass") String pass);

    @FormUrlEncoded
    @POST("placeorder.php")
    Call<LoginModel> placeOrder(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("getOrders.php")
    Call<OrderModel> getOrders(@Field("email") String email);
}
