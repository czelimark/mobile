package com.example.android_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android_mobile.Activities.LoggedInActivity;
import com.example.android_mobile.Data.Request;
import com.example.android_mobile.Data.Task;
import com.example.android_mobile.Data.User;
import com.example.android_mobile.Fragments.LoginFragment;
import com.example.android_mobile.Fragments.OfflineFragment;

import java.net.InetAddress;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    private Handler checkForInternet;
    private Runnable runnable ;
    private Integer count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskUserViewModel.createInstance(getApplicationContext(),this);
        model= TaskUserViewModel.getInstance();

        checkForInternet= new Handler();
        runnable= new Runnable()
        {
            @Override
            public void run()
            {
                if(count>=4)
                {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    LoginFragment loginFragment= new LoginFragment();
                    transaction.replace(R.id.scrollView,loginFragment);
                    //transaction.replace(R.id.scrollView,loginFragment);
                    transaction.commitAllowingStateLoss();
                    Toast.makeText(getApplicationContext(),"Connection established!",Toast.LENGTH_LONG).show();
                    checkForInternet.removeCallbacks(runnable);
                }
                else
                {
                    count++;
                    checkForInternet.postDelayed(this, 2500);
                }
            }
        };

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
         if(false)//checkNetworkConnection())
         {
             LoginFragment loginFragment= new LoginFragment();
            transaction.replace(R.id.scrollView,loginFragment);
            //transaction.replace(R.id.scrollView,loginFragment);
            transaction.commit();
         }
         else
         {
             OfflineFragment offlineFragment = new OfflineFragment();
             transaction.replace(R.id.scrollView,offlineFragment);
             transaction.commit();
             checkForInternet.postDelayed(runnable,0);
         }

    }
    public Call<List<Task>> getTasks()
    {
        return model.getTasksFromRemoteRepository();
    }
    public void loginPushed(final String username, final String password)
    {
        Call<Request<Boolean>> call= model.login(username,password);
        call.enqueue(new Callback<Request<Boolean>>() {
            @Override
            public void onResponse(Call<Request<Boolean>> call, Response<Request<Boolean>> response)
            {
                if(response.body().getT()==true)
                {
                    try
                    {
                        try {
                            model.addUserLocal(username, password);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        model.setLocalToken(response.body().getToken());
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                        // intent.putExtra("model",model);
                        startActivity(intent);
                    }
                    catch (Exception e )
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Request<Boolean>> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        });
        //model.addUser(username,password);
    }
    public void checkUserPushed(String  username)
    {
        User user= model.getUserFromLocalStorage(username).getValue();
        if(user != null)
        {
            Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(getApplicationContext(),"doesn't exist!",Toast.LENGTH_LONG).show();
    }

    private boolean checkNetworkConnection()
    {
        try
        {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private TaskUserViewModel model;
}


