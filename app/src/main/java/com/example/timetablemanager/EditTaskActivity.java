package com.example.timetablemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.timetablemanager.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.timetablemanager.EXTRA_DESCRIPTION";
    public static final String EXTRA_TIME = "com.example.timetablemanager.EXTRA_TIME";
    public static final String EXTRA_ID = "com.example.timetablemanager.EXTRA_ID";
    EditText edit_title, edit_des;
    String title, description, time;
    String new_title, new_description;
    int id, hour, minute;
    Intent intent;
    Calendar calendar;
    Button btn_edit_time;
    String amPm ="";
    TaskViewModel taskViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Task");
        edit_title = findViewById(R.id.edit_title);
        edit_des = findViewById(R.id.edit_des);
        btn_edit_time = findViewById(R.id.btn_edit_time);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        intent = getIntent();
        title = intent.getStringExtra(EXTRA_TITLE);
        description = intent.getStringExtra(EXTRA_DESCRIPTION);
        time = intent.getStringExtra(EXTRA_TIME);
        id = intent.getIntExtra(EXTRA_ID, -1);
        edit_title.setText(title);
        edit_des.setText(description);
        btn_edit_time.setText(time);
        btn_edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
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
                if(!DateFormat.is24HourFormat(EditTaskActivity.this)){

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
                btn_edit_time.setText(time);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this, onTimeSetListener,currentHour,currentMinute, DateFormat.is24HourFormat(EditTaskActivity.this));
        timePickerDialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_task_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.save_option){
            new_title = edit_title.getText().toString();
            new_description = edit_des.getText().toString();
            if(id!=-1) {
                if(TextUtils.isEmpty(new_title)||TextUtils.isEmpty(new_description)){
                    if(TextUtils.isEmpty(new_title)) {
                        edit_title.setError("Title is required!");
                    }
                    if(TextUtils.isEmpty(new_description) && !TextUtils.isEmpty(new_title)) {
                        edit_des.setText("");
                        editTaskAlarm();
                    }
                }
                else{
                    editTaskAlarm();
                }

            }
            else{
                Toast.makeText(this, "Failed to update the task!", Toast.LENGTH_LONG).show();
            }


        }
        return true;
    }
    public void editTaskAlarm(){
        new_title = edit_title.getText().toString();
        new_description = edit_des.getText().toString();
        Task task = new Task(new_title, new_description, time);
        task.setId(id);
        taskViewModel.update(task);
        Toast.makeText(EditTaskActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(EditTaskActivity.this,AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.EXTRA_TITLE, task.getTitle());
        intent.putExtra(AlarmReceiver.EXTRA_DESCRIPTION, task.getDescription());
        intent.putExtra(AlarmReceiver.EXTRA_ID, task.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) ((task.getId())+System.currentTimeMillis()), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(EditTaskActivity.this, "Alarm updated successfully", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
        finish();
    }
}
