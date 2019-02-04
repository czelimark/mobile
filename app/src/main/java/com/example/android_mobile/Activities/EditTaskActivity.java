package com.example.android_mobile.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_mobile.Data.Task;
import com.example.android_mobile.R;
import com.example.android_mobile.TaskUserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskActivity extends AppCompatActivity
{
    private TaskUserViewModel model;
    private ProgressBar progressBar;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView seekBarTextView;
    private SeekBar seekBar;
    private int taskId;
    private Boolean updated=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit_layout);
        Intent intent= getIntent();
         taskId= intent.getExtras().getInt("taskId");
        progressBar = findViewById(R.id.progressBar);
        nameTextView= findViewById(R.id.nameEditText);
        descriptionTextView= findViewById(R.id.descriptionEditText);
        progressBar.setVisibility(View.INVISIBLE);
        model= TaskUserViewModel.getInstance();
        seekBarTextView = findViewById(R.id.seekBarTextView);
        seekBarTextView.setText(String.valueOf(0));
        seekBar =findViewById(R.id.seekBar);

        ((seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                seekBarTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        ((Button)findViewById(R.id.saveButton)).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    saveButtonPushed();
                }
            });
        ((Button)findViewById(R.id.cancelButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditTaskActivity.this.finish();
            }
        });
        ((Button)findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteButtonPushed();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        model.getTask(taskId).enqueue(new Callback<Task>()
        {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response)
            {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Task task = response.body();
                nameTextView.setText(task.getName());
                descriptionTextView.setText(task.getDescription());
                seekBar.setProgress(task.getDificulty());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t)
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void saveButtonPushed()
    {
        try
        {
            Task task = new Task();
            task.setName(nameTextView.getText().toString());
            task.setDescription(descriptionTextView.getText().toString());
            task.setDificulty(Byte.valueOf(seekBarTextView.getText().toString()));
            task.setId(taskId);

            model.updateTask(task).enqueue(new Callback<Task>()
            {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response)
                {
                    nameTextView.setText(response.body().getName());
                    descriptionTextView.setText(response.body().getDescription());
                    seekBar.setProgress(response.body().getDificulty());
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                    builder.setMessage("Task updated !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {updated=true;
                            onExit();
                        }
                    });
                    builder.create().show();
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                    builder.setMessage("Task not updates updated !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    });builder.create().show();

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void deleteButtonPushed()
    {
        try
        {
            model.deleteTask(taskId).enqueue(new Callback<Boolean>()
            {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                    builder.setMessage("Task deleted !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { updated=true;onExit();}
                    });builder.create().show();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                    builder.setMessage("Task not deleted !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {onExit(); }
                    });builder.create().show();
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e)
        {

        }
    }

    private void onExit()
    {
        Intent intent = new Intent();
        intent.putExtra("res",1);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        onExit();
    }
}
