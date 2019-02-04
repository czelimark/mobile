package com.example.android_mobile.Data;

public class Request<T>
{
    private T t;

    private String token;

    public Request()
    {
    }

    public Request(T t, String token)
    {
        this.t = t;
        this.token = token;
    }


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
