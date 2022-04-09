package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {
    Button btn_task_time;
    EditText input_task_title, input_task_des;
    int hour, minute;
    String amPm="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a Task");
        btn_task_time = findViewById(R.id.btn_task_time);
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
                        btn_task_time.setText(String.format(Locale.getDefault(), "%02d:%02d"+amPm, hour, minute));

                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, onTimeSetListener,currentHour,currentMinute, DateFormat.is24HourFormat(AddTaskActivity.this));
                timePickerDialog.show();
            }
        });
    }
}