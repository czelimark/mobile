package com.example.android_mobile.Repositories.Remote;

import com.example.android_mobile.Data.Request;
import com.example.android_mobile.Data.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserRemoteRepository
{
    @GET("/users/loginSafe")
    Call<Request<Boolean>>  login(@Query("user") String username, @Query("pass") String password);


    @GET("/users/get")
    Call<User> getUser(@Query("username") String username);
}
