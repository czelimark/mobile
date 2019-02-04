package com.example.android_mobile.Repositories.Remote;

import com.example.android_mobile.Data.Request;
import com.example.android_mobile.Data.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskRemoteRepository
{
    @GET("/tasks/get")
    Call<Task> getTask(@Query("id") Integer Id);

    @GET("/tasks/allSafe")
    Call<List<Task>> getAll(@Query("token") String token);

    @PUT("/tasks/update/")
    Call<Task> updateTask(@Body Request<Task> requestTask);

    @DELETE("/tasks/delete/{id}")
    Call<Boolean> deleteTask(@Path("id") Request<Integer> requestId);

    @POST("/tasks/saveSafe")
    Call<Task> addTask(@Body Request<Task> taskRequest);
}
