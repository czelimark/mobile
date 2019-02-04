package com.example.android_mobile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.example.android_mobile.Data.Request;
import com.example.android_mobile.Data.Task;
import com.example.android_mobile.Data.User;
import com.example.android_mobile.Repositories.LocalStorage.LocalTaskRepository;
import com.example.android_mobile.Repositories.LocalStorage.LocalUserRepository;
import com.example.android_mobile.Repositories.LocalStorage.UserLocalDatabase;
import com.example.android_mobile.Repositories.Remote.RetrofitClientInstance;
import com.example.android_mobile.Repositories.Remote.TaskRemoteRepository;
import com.example.android_mobile.Repositories.Remote.UserRemoteRepository;

import java.util.List;

import retrofit2.Call;

public class TaskUserViewModel extends ViewModel {
    private LiveData<User> userLiveData;
    private LiveData<String> localToken;
    private LocalUserRepository localUserRepository;
    private UserRemoteRepository remoteUserRepository;
    private TaskRemoteRepository taskRemoteRepository;
    private LocalTaskRepository localTaskRepository;
    private static TaskUserViewModel taskUserViewModel;

    private TaskUserViewModel(Context context) {
        localUserRepository = LocalUserRepository.getInstance(UserLocalDatabase.getUserInstance(context).userDao());
        localTaskRepository = LocalTaskRepository.getInstance(UserLocalDatabase.getTaskInstance(context).taskDao());
        remoteUserRepository = RetrofitClientInstance.getUsersRetrofitInstance().create(UserRemoteRepository.class);
        taskRemoteRepository = RetrofitClientInstance.getTasksRetrofitInstance().create(TaskRemoteRepository.class);

    }

    public void setLocalToken(String localToken) {
        this.localToken = new MutableLiveData<>();
        ((MutableLiveData<String>) this.localToken).setValue(localToken);
    }

    public String getToken()
    {
        return localToken.getValue();
    }

    public static void createInstance(Context context, FragmentActivity activity)
    {
        taskUserViewModel=ViewModelProviders.of(activity,new TaskUserViewModel.Factory(context)).get(TaskUserViewModel.class);
    }

    public static TaskUserViewModel getInstance(){return taskUserViewModel;}

    public  void addUserLocal(String username,String password)
    {
        localUserRepository.addUser(username,password);
    }
    public LiveData<User> getUserFromLocalStorage(String username)
    {
        userLiveData= localUserRepository.getUser(username);
        return userLiveData;
    }


    public  Call<Request<Boolean>> login(String username, String password)
    {
        return remoteUserRepository.login(username,password);
    }

    public  void deleteUserFromLocalStorage()
    {
        localUserRepository.emptyUsers();
    }

    public  Call<User> getUserFromRemoteRepository(String username)
    {
        return remoteUserRepository.getUser(username);
    }

    public  Call<List<Task>> getTasksFromRemoteRepository()
    {
        return taskRemoteRepository.getAll(localToken.getValue());
    }

    public Call<Task> getTask(Integer id)
    {
        return taskRemoteRepository.getTask(id);
    }

    public Call<Task> updateTask(Task task)
    {
        return taskRemoteRepository.updateTask(new Request<Task>(task,localToken.getValue()));
    }

    public Call<Boolean> deleteTask(Integer integer)
    {
        return taskRemoteRepository.deleteTask(new Request<Integer>(integer,localToken.getValue()));
    }

    public List<Task> getTasksFromLocalRepository()
    {
        return localTaskRepository.getTasks();
    }

    public Call<Task> addTask(Task task)
    {
        return taskRemoteRepository.addTask(new Request<>(task,localToken.getValue()));
    }

    public List<User> getUsers()
    {
        return localUserRepository.getUsers();
    }

    public void deleteLocalTasks() {localTaskRepository.deleteTasks();}
    public void  addLocalTask(Task task){localTaskRepository.addTask(task);}

    private static class Factory implements ViewModelProvider.Factory
    {
        private final Context context;

        Factory(Context context)
        {
            this.context=context;
        }

        public <T extends ViewModel> T create(Class<T> modelClass)
        {
            return (T) new TaskUserViewModel(context);
        }
    }
}
