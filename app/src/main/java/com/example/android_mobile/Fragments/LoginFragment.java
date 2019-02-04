package com.example.android_mobile.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_mobile.Data.User;
import com.example.android_mobile.MainActivity;
import com.example.android_mobile.R;
import com.example.android_mobile.TaskUserViewModel;

import java.util.List;

public class LoginFragment extends Fragment
{
    @Override
    public void onCreate(@Nullable Bundle savedInstance)
    {

        super.onCreate(savedInstance);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.login_fragment,container,false);
        loginButton= view.findViewById(R.id.loginButton);
        lookButton = view.findViewById(R.id.searchButton);
        usernameTextView = view.findViewById(R.id.usernameEditText);
        passwordTextView = view.findViewById(R.id.passwordEditText);
        model = TaskUserViewModel.getInstance();
        List<User> users = model.getUsers();
        if(users.size()>0)
        {
            User user = users.get(0);
            usernameTextView.setText(user.getUserId());
            passwordTextView.setText(user.getPassword());
        }
        Activity activity = getActivity();
        final MainActivity mainActivity =(MainActivity) activity;
        lookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mainActivity.checkUserPushed(usernameTextView.getText().toString());
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.loginPushed(usernameTextView.getText().toString(),passwordTextView.getText().toString());
            }
        });
        return view;
    }

    private Button loginButton;
    private Button lookButton;
    private TextView usernameTextView;
    private TextView passwordTextView;
    private TaskUserViewModel model;

}
