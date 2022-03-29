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
 * Use the {@link PostponedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostponedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerViewPostponed;
    ArrayList<TaskModel> arrPostponed = new ArrayList<>();

    public PostponedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostponedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostponedFragment newInstance(String param1, String param2) {
        PostponedFragment fragment = new PostponedFragment();
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
        View v = inflater.inflate(R.layout.fragment_postponed, container, false);
        recyclerViewPostponed = v.findViewById(R.id.recyclerViewPostponed);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPostponed.setLayoutManager(layoutManager);


        arrPostponed.add(new TaskModel("A", "xyz","02:00 PM"));
        arrPostponed.add(new TaskModel("B", "xyz1","05:00 PM"));
        arrPostponed.add(new TaskModel("C", "xyz2","07:00 PM"));
        arrPostponed.add(new TaskModel("D", "xyz3","09:00 AM"));
        arrPostponed.add(new TaskModel("E", "xyz4","12:00 PM"));
        arrPostponed.add(new TaskModel("F", "xyz5","03:00 PM"));
        arrPostponed.add(new TaskModel("G", "xyz6","10:00 AM"));
        arrPostponed.add(new TaskModel("H", "xyz7","08:00 PM"));

        RecyclerPostponedAdapter adapter = new RecyclerPostponedAdapter(arrPostponed);
        recyclerViewPostponed.setAdapter(adapter);
        return v;
    }
}