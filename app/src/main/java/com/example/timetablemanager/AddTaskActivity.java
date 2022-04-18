package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timetablemanager.databinding.ActivityAddTaskBinding;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {

    Button btn_task_time, add_task_alarm;
    EditText input_task_title, input_task_des;
    String title, description, time;
    int id;
    String amPm="";
    TaskViewModel taskViewModel;
    private Calendar calendar;

    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.timetablemanager.databinding.ActivityAddTaskBinding addTaskBinding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(addTaskBinding.getRoot());
        createNotificationChannel();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a Task");
        btn_task_time = findViewById(R.id.btn_task_time);
        add_task_alarm = findViewById(R.id.add_task_alarm);
        input_task_title = findViewById(R.id.input_task_title);
        input_task_des = findViewById(R.id.input_task_des);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        btn_task_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        addTaskBinding.addTaskAlarm.setOnClickListener(new View.OnClickListener() {
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
                        addTaskAlarm();
                    }
                    if(TextUtils.isEmpty(time)){
                        Toast.makeText(AddTaskActivity.this, "Set a time for your task!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    addTaskAlarm();
                }
            }
        });

    }

    private void showTimePicker() {
        calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                calendar.set(Calendar.HOUR_OF_DAY , selectedHour);
                calendar.set(Calendar.MINUTE , selectedMinute);
                calendar.set(Calendar.SECOND , 0);
                calendar.set(Calendar.MILLISECOND , 0);
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

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Tasks to be performed channel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("task",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void addTaskAlarm(){
        Task task = new Task(title, description, time);
        taskViewModel.insert(task);
        Toast.makeText(AddTaskActivity.this, "Task added", Toast.LENGTH_SHORT).show();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AddTaskActivity.this,AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.EXTRA_TITLE, task.getTitle());
        intent.putExtra(AlarmReceiver.EXTRA_DESCRIPTION, task.getDescription());
        intent.putExtra(AlarmReceiver.EXTRA_ID, task.getId());
        Intent i = new Intent(AddTaskActivity.this, EditTaskActivity.class);
        i.putExtra(EditTaskActivity.EXTRA_ID, task.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTaskActivity.this, (int) task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(AddTaskActivity.this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

}