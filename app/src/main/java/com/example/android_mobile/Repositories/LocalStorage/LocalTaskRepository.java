package com.example.android_mobile.Repositories.LocalStorage;

import com.example.android_mobile.Data.Task;

import java.util.List;

public class LocalTaskRepository
{
    private TaskDao taskDao;
    private static LocalTaskRepository repository;

    private LocalTaskRepository(TaskDao taskDao)
    {
        this.taskDao = taskDao;
    }

    public static LocalTaskRepository getInstance(TaskDao taskDao)
    {
        if(repository== null)
            repository= new LocalTaskRepository(taskDao);
        return repository;
    }

    public List<Task> getTasks()
    {
        return taskDao.getTasks();
    }
    public void deleteTasks()
    {
        taskDao.deleteTasks();
    }

    public void addTask(Task task)
    {
        taskDao.insert(task);
    }
}
