package com.example.timetablemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerTasksPendingAdapter extends RecyclerView.Adapter<RecyclerTasksPendingAdapter.ViewHolder> {
    ArrayList<TaskModel> arrPending;
    RecyclerTasksPendingAdapter(ArrayList<TaskModel> arrPending){
        this.arrPending = arrPending;
        // so now we got the arraylist.
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.task_title.setText(arrPending.get(position).title);
    holder.task_description.setText(arrPending.get(position).description);
    holder.task_time.setText(arrPending.get(position).time);
    }

    @Override
    public int getItemCount() {
        return arrPending.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView task_title, task_description, task_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title = itemView.findViewById(R.id.task_title);
            task_description = itemView.findViewById(R.id.task_description);
            task_time = itemView.findViewById(R.id.task_time);
        }
    }
}
