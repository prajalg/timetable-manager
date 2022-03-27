package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditNameDialogActivity extends AppCompatActivity {
    Button cancel_name_btn, save_name_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_dialog);
        cancel_name_btn = findViewById(R.id.cancel_name_btn);
        save_name_btn= findViewById(R.id.save_name_btn);
        cancel_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}