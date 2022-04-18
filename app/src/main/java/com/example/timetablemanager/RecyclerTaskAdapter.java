package com.example.timetablemanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<RecyclerTaskAdapter.ViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;
    Context context;
    RecyclerTaskAdapter(Context context){
        this.context = context;
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
        holder.task_title.setText(tasks.get(position).getTitle());
        holder.task_description.setText(tasks.get(position).getDescription());
        holder.task_time.setText(tasks.get(position).getTime());
        //now we have bind the data to the view holder.
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }
    public Task getTaskAt(int position){
        return tasks.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView task_title, task_description, task_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title = itemView.findViewById(R.id.task_title);
            task_description = itemView.findViewById(R.id.task_description);
            task_time = itemView.findViewById(R.id.task_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if(listener!=null && position!= RecyclerView.NO_POSITION) {
                        listener.onItemClick(tasks.get(position));
                    }
                }
            });
        }

    }
    public interface OnItemClickListener{
        void onItemClick(Task task);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
