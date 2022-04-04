package com.example.timetablemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerViewTasks;
    ArrayList<TaskModel> arrTasks = new ArrayList<>();

    public TasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerViewTasks = v.findViewById(R.id.recyclerViewTasks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   //note that we cannot use 'this' as context.
        recyclerViewTasks.setLayoutManager(layoutManager);

        arrTasks.add(new TaskModel("Mechanics", "Do questions on virtual work principle", "07:30 AM"));
        arrTasks.add(new TaskModel("Physics Lab class", "Join the lab class on zoom", "10:00 AM"));
        arrTasks.add(new TaskModel("Electronics", "Practice diode, op-amp numerical questions", "12:30 PM"));
        arrTasks.add(new TaskModel("Mathematics", "Do tutorial sheet 3", "02:00 PM"));
        arrTasks.add(new TaskModel("Electronics class", "Join the class on zoom", "03:00 PM"));
        arrTasks.add(new TaskModel("Mathematics class", "Join the class on zoom", "04:00 PM"));
        arrTasks.add(new TaskModel("Economics & Finance class", "Join the class on zoom", "05:00 PM"));
        arrTasks.add(new TaskModel("Physics lab report file", "Write experiment 5", "07:30 PM"));
        arrTasks.add(new TaskModel("Mechanics Tutorial sheet", "Do tutorial sheet on Module 7", "09:30 AM"));

        // making object of RecyclerTaskAdapter class:

        RecyclerTaskAdapter adapter = new RecyclerTaskAdapter(arrTasks, getContext());
        recyclerViewTasks.setAdapter(adapter);
        return v;
    }
}