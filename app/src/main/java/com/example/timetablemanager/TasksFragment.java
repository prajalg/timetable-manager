package com.example.timetablemanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
    private TaskViewModel taskViewModel;
    FloatingActionButton add_task_btn;
    public static final int ADD_TASK_REQUEST_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);
        add_task_btn = v.findViewById(R.id.add_task_btn);
        recyclerViewTasks = v.findViewById(R.id.recyclerViewTasks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   //note that we cannot use 'this' as context.
        recyclerViewTasks.setLayoutManager(layoutManager);
        final RecyclerTaskAdapter adapter = new RecyclerTaskAdapter(getContext());
        recyclerViewTasks.setAdapter(adapter);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Toast.makeText(getContext(), "onChanged", Toast.LENGTH_SHORT).show();
                adapter.setTasks(tasks);
            }
        });
        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddTaskActivity.class);
               startActivity(intent);

            }
        });

        Bundle data = getArguments();
        if(data!=null){
            Task task = new Task(data.getString("EXTRA_TITLE"), data.getString("EXTRA_DESCRIPTION"),data.getString("EXTRA_TIME"));
            taskViewModel.insert(task);
        }

        return v;
    }
}