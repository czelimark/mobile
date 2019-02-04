package com.example.android_mobile.Repositories.LocalStorage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.android_mobile.Data.Task;
import com.example.android_mobile.Data.User;


@Database(entities = {User.class,Task.class},version = 2)
public abstract class UserLocalDatabase extends android.arch.persistence.room.RoomDatabase
{
    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public static UserLocalDatabase userInstance;
    public static UserLocalDatabase taskInstance;

    public static UserLocalDatabase getUserInstance(Context context)
    {
        if(userInstance ==null)
        {
            userInstance = Room.databaseBuilder(context,UserLocalDatabase.class,"users").allowMainThreadQueries().build();
        }
        return userInstance;
    }

    public static UserLocalDatabase getTaskInstance(Context context)
    {
        if(taskInstance == null)
        {
            taskInstance = Room.databaseBuilder(context,UserLocalDatabase.class,"tasks").allowMainThreadQueries().build();
        }
        return taskInstance;
    }
}
