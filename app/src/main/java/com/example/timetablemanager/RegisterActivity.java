package com.example.timetablemanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView login_here;
    EditText input_reg_email, input_reg_password;
    Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        input_reg_email = findViewById(R.id.input_reg_email);
        input_reg_password = findViewById(R.id.input_reg_password);
        button_register = findViewById(R.id.button_register);
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
        String email = input_reg_email.getText().toString();
        String password = input_reg_password.getText().toString();

        if(TextUtils.isEmpty(email)){
            input_reg_email.setError("Email is mandatory");
        }
        else if(TextUtils.isEmpty(password)){
            input_reg_password.setError("Password is mandatory");
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registration Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}