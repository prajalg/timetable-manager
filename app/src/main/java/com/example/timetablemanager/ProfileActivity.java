package com.example.timetablemanager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    Button changePswd;
    FloatingActionButton btn_edit_name, btn_edit_email;
    TextView edit_field;
    Button cancel_name_btn,cancel_email_btn, save_name_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_section);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
        btn_edit_name = findViewById(R.id.btn_edit_name);
        btn_edit_email = findViewById(R.id.btn_edit_email);
        changePswd = findViewById(R.id.btn_change_pswd);
        edit_field = findViewById(R.id.edit_field);
        changePswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ChangePswdActivity.class));
            }
        });

        btn_edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.activity_edit_name_dialog);
                dialog.show();
                cancel_name_btn = dialog.findViewById(R.id.cancel_name_btn);
                cancel_name_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        btn_edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.activity_edit_email_dialog);
                dialog.show();
                cancel_email_btn = dialog.findViewById(R.id.cancel_email_btn);
                cancel_email_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


    }
}
