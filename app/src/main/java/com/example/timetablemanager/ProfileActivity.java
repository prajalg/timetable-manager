package com.example.timetablemanager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    Button changePswd, apply_btn;
    FloatingActionButton btn_edit_name, btn_edit_email, add_photo_btn;
    TextView display_name, display_email;
    EditText editName, editEmail;
    String name, email, photo;
    Button cancel_name_btn,cancel_email_btn, save_name_btn, save_email_btn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getCurrentUser().getUid();
    DocumentReference userRef = db.collection("Users").document(uid);

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
        photo = "";
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
                editName.setText(name);
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
                editEmail.setText(email);
                save_email_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email = editEmail.getText().toString();
                        display_email.setText(email);
                        Objects.requireNonNull(mAuth.getCurrentUser()).updateEmail(email);
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

    @Override
    protected void onStart() {
        super.onStart();
        userRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(ProfileActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(value.exists()) {
                    User user = value.toObject(User.class);
                    name = user.getName();
                    email = user.getEmail();
                    display_name.setText(name);
                    display_email.setText(email);
                } else {
                    display_name.setText("");
                    display_email.setText("");
                }

            }
        });
    }

    public void saveUser(View v){

            User user = new User(name, email, photo);
            userRef.set(user)
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

