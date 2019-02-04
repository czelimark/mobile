package com.example.android_mobile.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_mobile.Data.Task;
import com.example.android_mobile.R;
import com.example.android_mobile.TaskUserViewModel;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggedInActivity extends AppCompatActivity
{
    private boolean firstTimePressed = true;
    private long lastTimePressed;
    private RecyclerView recyclerView;
    private TaskUserViewModel model;
    private RecyclerViewAdapter adapter;
    private Handler handler ;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();
        ((Button)findViewById(R.id.emailButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ndreizampano@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(LoggedInActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.setOnItemClick(new RecyclerViewAdapter.OnItemClick()
        {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra("taskId", task.getId());
                startActivityForResult(intent, 1);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(), task.toString(), Toast.LENGTH_LONG).show();
            }
        });
        model = (TaskUserViewModel.getInstance());
        //model.deleteLocalTasks();
        model.getTasksFromRemoteRepository().enqueue(new Callback<List<Task>>()
        {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response)
            {
                if(response.body()!=null)
                {
                    model.deleteLocalTasks();
                    response.body().forEach(new Consumer<Task>() {
                        @Override
                        public void accept(Task task) {
                            try {
                                model.addLocalTask(task);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                    System.out.println("task locally saved");
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) { }
        });
        adapter.setTaskUserViewModel(model);
        adapter.refresh();
        recyclerView.setAdapter(adapter);

        ((Button)findViewById(R.id.addButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startAddNewTaskActivity();
            }
        });
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                adapter.refresh();
                handler.postDelayed(this,5000);
                System.out.println("refreshed");
            }
        };
        handler.postDelayed(runnable,0);
    }
    private void startAddNewTaskActivity()
    {
        Intent intent = new Intent(this,AddNewTaskActivity.class);
        startActivityForResult(intent,2);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                adapter.refresh();
                System.out.println("onActivityREsult");
            }
        }
        if(requestCode==2)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                adapter.refresh();
                System.out.println("added new task!");
            }
        }

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Really wanna log out?...");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                LoggedInActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getApplicationContext(),"You are not logged out!",Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
        //super.onBackPressed();
//        if(firstTimePressed)
//        {
//            firstTimePressed= false;
//            lastTimePressed = System.currentTimeMillis();
//        }
//        else
//        {
//            if(System.currentTimeMillis()-lastTimePressed<2000)
//                LoggedInActivity.this.finish();
//            lastTimePressed= System.currentTimeMillis();
//        }

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        handler.removeCallbacks(runnable);

    }
}
