package com.example.android_mobile.Activities;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.android_mobile.Data.Task;
import com.example.android_mobile.R;
import com.example.android_mobile.TaskUserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{
    private  List<Task> tasks;
    private OnItemClick onItemClick;
    private TaskUserViewModel taskUserViewModel;
    private int lastPosition=-1;
    public interface OnItemClick
    {
        void onItemClick(Task task);
    }
    public RecyclerViewAdapter()
    {
    }

    public void setTaskUserViewModel(TaskUserViewModel taskUserViewModel)
    {
        this.taskUserViewModel = taskUserViewModel;
    }
    public void refresh()
    {
        taskUserViewModel.getTasksFromRemoteRepository().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response)
            {
                if(response!=null)
                setTasks(response.body());
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });
    }

    private void setTasks(List<Task> tasks)
    {
        if(this.tasks!=null&&this.tasks.size()>0)
            notifyItemRangeRemoved(0, this.tasks.size());
        this.tasks=tasks;
        this.notifyItemRangeInserted(0,tasks.size());
        System.out.println("items set!");
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(1500);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    public void setOnItemClick(OnItemClick onItemClick)
    {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ConstraintLayout constraintLayout= (ConstraintLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_view_layout,viewGroup,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(constraintLayout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i)
    {
        if(tasks.size()>i)
        {
            recyclerViewHolder.setTextName(tasks.get(i).getName());
            recyclerViewHolder.setTextDescription(tasks.get(i).getDescription());
            final Task task= tasks.get(i);
            recyclerViewHolder.getLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    onItemClick.onItemClick(task);
                }
            });
            setAnimation(recyclerViewHolder.layout,i);
        }
    }

    @Override
    public int getItemCount()
    {
        if(tasks!=null)
        return tasks.size();
        return 0;
    }

    public  class RecyclerViewHolder extends RecyclerView.ViewHolder
    {


        private ConstraintLayout layout;
        private TextView nameTextView;
        private TextView descriptionTextView;

        public RecyclerViewHolder(@NonNull ConstraintLayout layout)
        {
            super(layout);
            nameTextView= layout.findViewById(R.id.nameTextView) ;
            this.layout= layout;
            descriptionTextView=layout.findViewById(R.id.descriptionTextView);

        }

        public ConstraintLayout getLayout()
        {
            return layout;
        }

        public void setTextName(String text)
        {
            nameTextView.setText("|"+text);
        }
        public void setTextDescription(String text)
        {
            descriptionTextView.setText("|"+text);
        }
    }
}
