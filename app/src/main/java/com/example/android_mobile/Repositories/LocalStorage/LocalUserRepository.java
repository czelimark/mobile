package com.example.android_mobile.Repositories.LocalStorage;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.android_mobile.Data.User;

import java.io.Serializable;
import java.util.List;

public class LocalUserRepository implements Serializable
{
    private UserDao userDao;
    private static LocalUserRepository repository;

    private LocalUserRepository(UserDao userDao)
    {
        this.userDao=userDao;
    }

    public static LocalUserRepository getInstance(UserDao userDao)
    {
        if(repository==null)
        {
            repository= new LocalUserRepository(userDao);
        }
        return repository;
    }
    public void addUser(String userId,String password)
    {
        User user= new User(userId,password);
        userDao.insert(user);
    }
    public LiveData<User> getUser(String username)
    {
        LiveData<User > userLiveData = new MutableLiveData<>();
        ((MutableLiveData<User>) userLiveData).setValue(userDao.getUser(username));
        return userLiveData;
    }

    public  void emptyUsers()
    {
        userDao.delete();
    }

    public List<User> getUsers()
    {
        return userDao.getUsers();

    }
}
