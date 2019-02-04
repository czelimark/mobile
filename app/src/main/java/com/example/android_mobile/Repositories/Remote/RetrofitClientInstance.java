package com.example.android_mobile.Repositories.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance
{
    private static Retrofit usersRetrofit;
    private static Retrofit tasksRetrofit;
    private static final String usersUrl="http://10.150.0.129:8080/";
    private static final String tasksUrl="http://10.150.0.129:8080/";
    //private static final String url="http://10.0.2.2:8080/";

    public static Retrofit getUsersRetrofitInstance()
    {
        if(usersRetrofit== null)
            usersRetrofit = new Retrofit.Builder().baseUrl(usersUrl).addConverterFactory(GsonConverterFactory.create()).build();
        return  usersRetrofit;
    }
    public static Retrofit getTasksRetrofitInstance()
    {
        if(tasksRetrofit==null)
            tasksRetrofit = new Retrofit.Builder().baseUrl(tasksUrl).addConverterFactory(GsonConverterFactory.create()).build();
        return tasksRetrofit;
    }
}
