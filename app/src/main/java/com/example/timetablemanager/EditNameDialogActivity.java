package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNameDialogActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.timetablemanager.EXTRA_NAME";
    Button cancel_name_btn, save_name_btn;
    EditText editName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_dialog);
        cancel_name_btn = findViewById(R.id.cancel_name_btn);
        save_name_btn= findViewById(R.id.save_name_btn);
        save_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName = findViewById(R.id.editName);
                String name = editName.getText().toString();
                Intent intent = new Intent(EditNameDialogActivity.this, ProfileActivity.class);
                intent.putExtra(EXTRA_NAME,name);
                startActivity(intent);
            }
        });
    }
}