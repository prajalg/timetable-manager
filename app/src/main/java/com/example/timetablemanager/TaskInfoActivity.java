package com.example.timetablemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class TaskInfoActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.timetablemanager.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.timetablemanager.EXTRA_DESCRIPTION";
    public static final String EXTRA_TIME = "com.example.timetablemanager.EXTRA_TIME";
    public static final String EXTRA_ID = "com.example.timetablemanager.EXTRA_ID";

    TextView info_title, info_des, info_time;
    String title, description, time;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Task Info");
        info_title = findViewById(R.id.info_title);
        info_des = findViewById(R.id.info_des);
        info_time = findViewById(R.id.info_time);
        Intent intent = getIntent();
        title = intent.getStringExtra(EXTRA_TITLE);
        description = intent.getStringExtra(EXTRA_DESCRIPTION);
        time = intent.getStringExtra(EXTRA_TIME);
        info_title.setText(title);
        info_des.setText(description);
        info_time.setText(time);
        id = intent.getIntExtra(EXTRA_ID, -1);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.edit_task){
            Intent intent = new Intent(TaskInfoActivity.this, EditTaskActivity.class);
            intent.putExtra(EditTaskActivity.EXTRA_ID, id);
            intent.putExtra(EditTaskActivity.EXTRA_TITLE, title);
            intent.putExtra(EditTaskActivity.EXTRA_DESCRIPTION, description);
            intent.putExtra(EditTaskActivity.EXTRA_TIME, time);
                startActivity(intent);
        }
        return true;
    }
}