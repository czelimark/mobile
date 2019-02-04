package com.example.android_mobile.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.android_mobile.Data.Task;

import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Task>
{
    private List<Task> tasks;
   // private
    public TaskListAdapter( @NonNull Context context, int resource,List<Task>tasks)
    {
       // List<String> strings = new ArrayList<>();
        //tasks.forEach((x)->strings.add(x.toString()));

        super(context, resource,tasks); this.tasks=tasks;
    }

    public void setTasks(List<Task> tasks)
    {
        this.tasks = tasks;
    }

    @Nullable
    @Override
    public Task getItem(int position)
    {
        //System.out.println(tasks.get(position).toString());
        return tasks.get(position);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }
}
