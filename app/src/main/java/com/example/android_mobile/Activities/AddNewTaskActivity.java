package com.example.android_mobile.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android_mobile.Data.Task;
import com.example.android_mobile.R;
import com.example.android_mobile.TaskUserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTaskActivity extends AppCompatActivity
{
    private TextView nameTextView;
    private TextView descritptionTextView;
    private TextView seekBarTextView;
    private SeekBar seekBar;
    private TaskUserViewModel model;
    private Boolean added= false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        nameTextView= findViewById(R.id.nameAddEditText);
        descritptionTextView= findViewById(R.id.descriptionAddEditText);
        seekBarTextView = findViewById(R.id.seekAddBarTextView);
        seekBarTextView.setText("20");
        seekBar= findViewById(R.id.seekAddBar);
        model = TaskUserViewModel.getInstance();
        ((Button)findViewById(R.id.addAddButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveTask();
            }
        });
        seekBar.setProgress(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarTextView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
    private void saveTask()
    {
        try
        {
            Task task =new Task();
            task.setName(nameTextView.getText().toString());
            task.setDescription(descritptionTextView.getText().toString());
            task.setId(0);
            task.setDificulty((byte) seekBar.getProgress());
            model.addTask(task).enqueue(new Callback<Task>() {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response)
                {
                    added=true;
                    showAlertDialog("Task added!","OK",null,null,null,null,null);
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t)
                {
                    showAlertDialog("Task not added!","OK",null,null,null,null,null);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void showAlertDialog(String message, String positiveButtonMessage, String neutralButtonMessage, String negativeButtonMessage, DialogInterface.OnClickListener positiveButtonOnClickListenr,DialogInterface.OnClickListener neutralButtonOnClickListenr,DialogInterface.OnClickListener negativeButtonOnClickListenr)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        if(positiveButtonMessage!=null)//&& positiveButtonOnClickListenr!=null)
        {
            builder.setPositiveButton(positiveButtonMessage,positiveButtonOnClickListenr);
        }
        if(negativeButtonMessage!=null)//&&negativeButtonOnClickListenr!=null)
            builder.setNegativeButton(negativeButtonMessage,negativeButtonOnClickListenr);
        if(neutralButtonMessage!=null)//&& neutralButtonOnClickListenr!=null)
            builder.setNeutralButton(neutralButtonMessage,neutralButtonOnClickListenr);
        builder.create().show();
    }

    @Override
    public void onBackPressed()
    {
        if(added)
        {
            Intent intent = new Intent();
            intent.putExtra("res1",2);
            setResult(Activity.RESULT_OK,intent);
        }
        super.onBackPressed();
    }
}
