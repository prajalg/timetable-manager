package com.example.timetablemanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView login_here;
    EditText input_reg_email, input_reg_password, input_name;
    Button button_register, save_name;
    String name, email, password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        input_reg_email = findViewById(R.id.input_reg_email);
        input_reg_password = findViewById(R.id.input_reg_password);
        input_name = findViewById(R.id.input_name);
        button_register = findViewById(R.id.button_register);
        save_name = findViewById(R.id.save_name);
        login_here = findViewById(R.id.login_here);

        button_register.setOnClickListener(view -> {
            createUser();
        });
        login_here.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void createUser(){
        email = input_reg_email.getText().toString();
        password = input_reg_password.getText().toString();

        if(TextUtils.isEmpty(email)){
            input_reg_email.setError("Email is mandatory");
        }
        else if(TextUtils.isEmpty(password)) {
            input_reg_password.setError("Password is mandatory");
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                            save_name.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    name = input_name.getText().toString();
                                    if (TextUtils.isEmpty(name)) {
                                        input_name.setError("Name is required!");
                                    } else {
                                        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                        DocumentReference userRef = db.collection("Users").document(uid);
                                        User user = new User(name, email);
                                        userRef.set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(RegisterActivity.this, "User data saved", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            });
                            Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        }
                    else{
                        save_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(RegisterActivity.this, "User is not registered yet!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Registration Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}