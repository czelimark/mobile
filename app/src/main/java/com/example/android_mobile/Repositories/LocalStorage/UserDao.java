package com.example.android_mobile.Repositories.LocalStorage;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android_mobile.Data.User;

import java.util.List;

@Dao
public interface UserDao
{
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE users.userId LIKE :user")
    User getUser(String user);

    @Query("SELECT * FROM users")
    List<User>getUsers();

    @Query("DELETE FROM users")
    void delete();
}
