package com.example.android_mobile.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android_mobile.R;
import com.example.android_mobile.TaskUserViewModel;

public class OfflineFragment extends Fragment
{

    private ListView listView;
    private TaskUserViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        model = TaskUserViewModel.getInstance();
        View view = inflater.inflate(R.layout.offline_fragment,container,false);
        listView = view.findViewById(R.id.listView);
        System.out.println(model.getTasksFromLocalRepository().size());
        TaskListAdapter adapter = new TaskListAdapter(getContext(),android.R.layout.simple_list_item_1,model.getTasksFromLocalRepository());

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Toast.makeText(getContext(),"No internat connection! \n Show local cached tasks!",Toast.LENGTH_LONG).show();
        showAlertDialog(getContext(),"No internat connection!\n Currently displaing cached tasks!","Ok","","",null,null,null);
        return view;
    }

    public void showAlertDialog(Context context,String message, String positiveButtonMessage, String neutralButtonMessage, String negativeButtonMessage, DialogInterface.OnClickListener positiveButtonOnClickListenr, DialogInterface.OnClickListener neutralButtonOnClickListenr, DialogInterface.OnClickListener negativeButtonOnClickListenr)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
}
