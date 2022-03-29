package com.example.timetablemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<RecyclerTaskAdapter.ViewHolder> {
    ArrayList<TaskModel> arrTasks;
    RecyclerTaskAdapter(ArrayList<TaskModel> arrTasks){
        this.arrTasks = arrTasks;
        // so now we got the arraylist.
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task_title.setText(arrTasks.get(position).title);
        holder.task_description.setText(arrTasks.get(position).description);
        holder.task_time.setText(arrTasks.get(position).time);
        //now we have bind the data to the view holder.
    }

    @Override
    public int getItemCount() {
        return arrTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView task_title, task_description, task_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title = itemView.findViewById(R.id.task_title);
            task_description = itemView.findViewById(R.id.task_description);
            task_time = itemView.findViewById(R.id.task_time);
        }

    }
}
