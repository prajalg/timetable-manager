package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {

    Button btn_task_time, add_task_alarm;
    EditText input_task_title, input_task_des;
    String title, description, time;

    int hour, minute;
    String amPm="";
    TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a Task");
        btn_task_time = findViewById(R.id.btn_task_time);
        add_task_alarm = findViewById(R.id.add_task_alarm);
        input_task_title = findViewById(R.id.input_task_title);
        input_task_des = findViewById(R.id.input_task_des);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
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
                        time = String.format(Locale.getDefault(), "%02d:%02d"+amPm, hour, minute);
                        btn_task_time.setText(time);

                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, onTimeSetListener,currentHour,currentMinute, DateFormat.is24HourFormat(AddTaskActivity.this));
                timePickerDialog.show();
            }
        });

        add_task_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = input_task_title.getText().toString();
                description = input_task_des.getText().toString();
                if(TextUtils.isEmpty(title)||TextUtils.isEmpty(description)||TextUtils.isEmpty(time)){
                    if(TextUtils.isEmpty(title)) {
                        input_task_title.setError("Title is required!");
                    }
                    if(TextUtils.isEmpty(description) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(time)) {
                        input_task_des.setText("");
                        saveTask();
                    }
                    if(TextUtils.isEmpty(time)){
                        Toast.makeText(AddTaskActivity.this, "Set a time for your task!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    saveTask();
                }
            }
        });

    }
    public void saveTask(){
        Task task = new Task(title, description, time);
        taskViewModel.insert(task);
        Toast.makeText(AddTaskActivity.this, "Task added", Toast.LENGTH_SHORT).show();
        finish();
    }
}