package com.example.android_mobile.Repositories.LocalStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android_mobile.Data.Task;

import java.util.List;

@Dao
public interface TaskDao
{
    @Insert
     void insert(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("DELETE FROM tasks")
    void deleteTasks();
}
