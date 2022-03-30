package com.example.timetablemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PendingTasksFragment extends Fragment {

    RecyclerView recyclerViewPending;
    ArrayList<TaskModel> arrPending = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pending_tasks, container, false);

        recyclerViewPending = v.findViewById(R.id.recyclerViewPending);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPending.setLayoutManager(layoutManager);

        //dummy data
        arrPending.add(new TaskModel("A", "abc","02:00 PM"));
        arrPending.add(new TaskModel("B", "abc11","05:00 PM"));
        arrPending.add(new TaskModel("C", "abc22","07:00 PM"));
        arrPending.add(new TaskModel("D", "abc33","09:00 AM"));
        arrPending.add(new TaskModel("E", "abc44","12:00 PM"));
        arrPending.add(new TaskModel("F", "abc55","03:00 PM"));
        arrPending.add(new TaskModel("G", "abc66","10:00 AM"));
        arrPending.add(new TaskModel("H", "abc77","08:00 PM"));

        RecyclerTasksPendingAdapter adapter = new RecyclerTasksPendingAdapter(arrPending);
        recyclerViewPending.setAdapter(adapter);

        return v;
    }
}