package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.timetablemanager.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.timetablemanager.EXTRA_DESCRIPTION";
    public static final String EXTRA_TIME = "com.example.timetablemanager.EXTRA_TIME";

    Button btn_task_time, add_task_alarm;
    EditText input_task_title, input_task_des;
    String input_time;
    int hour, minute;
    String amPm="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a Task");
        btn_task_time = findViewById(R.id.btn_task_time);
        add_task_alarm = findViewById(R.id.add_task_alarm);
        input_task_title = findViewById(R.id.input_task_title);
        input_task_des = findViewById(R.id.input_task_des);
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        btn_task_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        if(!DateFormat.is24HourFormat(AddTaskActivity.this)){

                            if(hour>=12){
                                amPm =" PM";
                                if(hour>12) {
                                    hour -= 12;
                                }
                            }
                            else {
                                amPm = " AM";
                                if(hour==0){
                                    hour += 12;
                                }
                            }
                        }
                        input_time = String.format(Locale.getDefault(), "%02d:%02d"+amPm, hour, minute);
                        btn_task_time.setText(input_time);

                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, onTimeSetListener,currentHour,currentMinute, DateFormat.is24HourFormat(AddTaskActivity.this));
                timePickerDialog.show();
            }
        });

        add_task_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }
    public void saveTask(){
        String title = input_task_title.getText().toString();
        String description = input_task_des.getText().toString();
        String time = input_time;
        Intent intent = new Intent(AddTaskActivity.this, TasksFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("EXTRA_TITLE",title);
        bundle.putString("EXTRA_DESCRIPTION",description);
        bundle.putString("EXTRA_TIME",time);
        TasksFragment tasksFragment = new TasksFragment();
        tasksFragment.setArguments(bundle);
        intent.putExtras(bundle);
    }
}