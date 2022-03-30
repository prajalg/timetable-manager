package com.example.timetablemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingTasksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerViewPending;
    ArrayList<TaskModel> arrPending = new ArrayList<>();

    public PendingTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingTasksFragment newInstance(String param1, String param2) {
        PendingTasksFragment fragment = new PendingTasksFragment();
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
        View v = inflater.inflate(R.layout.fragment_status, container, false);

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

        RecyclerPostponedAdapter adapter = new RecyclerPostponedAdapter(arrPending);
        recyclerViewPending.setAdapter(adapter);

        return v;
    }
}