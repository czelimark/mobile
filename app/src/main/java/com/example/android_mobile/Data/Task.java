package com.example.android_mobile.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tasks")
public class Task
{
    @NonNull
    @PrimaryKey
    private Integer id;
    private String name;
    private String description;
    private byte dificulty;


    public Task()
    {

    }

    public Task(Integer id, String name, String description, byte dificulty)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dificulty = dificulty;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public byte getDificulty()
    {
        return dificulty;
    }

    public void setDificulty(byte dificulty)
    {
        if(isDificultyValid(dificulty))
            this.dificulty = dificulty;
    }

    private boolean isDificultyValid(byte dificulty)
    {
        if(dificulty>0&&dificulty<101)
            return true;
        return false;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dificulty=" + dificulty +
                '}';
    }
}
