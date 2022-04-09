package com.example.timetablemanager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    Button changePswd, apply_btn;
    FloatingActionButton btn_edit_name, btn_edit_email, add_photo_btn;
    TextView display_name, display_email;
    EditText editName, editEmail;
    String name, email, photo;
    Button cancel_name_btn,cancel_email_btn, save_name_btn, save_email_btn;
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_section);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
        btn_edit_name = findViewById(R.id.btn_edit_name);
        btn_edit_email = findViewById(R.id.btn_edit_email);
        apply_btn = findViewById(R.id.apply_btn);
        changePswd = findViewById(R.id.btn_change_pswd);
        display_name = findViewById(R.id.name);
        display_email = findViewById(R.id.email);

        add_photo_btn = findViewById(R.id.add_photo_btn);
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
                save_name_btn = dialog.findViewById(R.id.save_name_btn);
                editName = dialog.findViewById(R.id.editName);
                save_name_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = editName.getText().toString();
                        display_name.setText(name);
                        dialog.dismiss();

                    }
                });
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
                save_email_btn = dialog.findViewById(R.id.save_email_btn);
                editEmail = dialog.findViewById(R.id.editEmail);
                save_email_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email = editEmail.getText().toString();
                        display_email.setText(email);
                        dialog.dismiss();

                    }
                });
            }
        });
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(view);
            }
        });
        add_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo ="";
            }
        });
    }
    public void saveUser(View v){
        Map<String, Object> user = new HashMap<>();
            user.put(KEY_NAME, name);
            user.put(KEY_EMAIL, email);
            user.put(KEY_PHOTO, photo);

            db.collection("Users").document().set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProfileActivity.this, "User data saved", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
    }
}
