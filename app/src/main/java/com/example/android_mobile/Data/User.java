package com.example.android_mobile.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class User
{
    @NonNull
    @PrimaryKey
    private String userId;
    private String password;
    public User()
    {

    }

    public User(String userId, String password)
    {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId()
    {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

