package com.example.timetablemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CompletedTasksFragment extends Fragment {
    RecyclerView recyclerViewCompleted;
    ArrayList<TaskModel> arrCompleted = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_status, container, false);

        recyclerViewCompleted = v.findViewById(R.id.recyclerViewCompleted);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCompleted.setLayoutManager(layoutManager);
        //dummy data

        arrCompleted.add(new TaskModel("A", "abc","02:00 PM"));
        arrCompleted.add(new TaskModel("B", "abc1","05:00 PM"));
        arrCompleted.add(new TaskModel("C", "abc2","07:00 PM"));
        arrCompleted.add(new TaskModel("D", "abc3","09:00 AM"));
        arrCompleted.add(new TaskModel("E", "abc4","12:00 PM"));
        arrCompleted.add(new TaskModel("F", "abc5","03:00 PM"));
        arrCompleted.add(new TaskModel("G", "abc6","10:00 AM"));
        arrCompleted.add(new TaskModel("H", "abc7","08:00 PM"));

        RecyclerPostponedAdapter adapter = new RecyclerPostponedAdapter(arrCompleted);
        recyclerViewCompleted.setAdapter(adapter);

        return v;
    }
}