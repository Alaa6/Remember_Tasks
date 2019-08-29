package com.example.remember_tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context mContext ;
    ArrayList<Tasks> taskArrayList =new ArrayList<>();


    public TaskAdapter(Context context , ArrayList<Tasks> taskList) {

        this.mContext =context;
        this.taskArrayList = taskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_content , parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.taskContent.setText(taskArrayList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView taskContent ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskContent = itemView.findViewById(R.id.taskContentId);
        }
    }
}
