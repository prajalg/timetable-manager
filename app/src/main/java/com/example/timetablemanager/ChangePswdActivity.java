package com.example.timetablemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangePswdActivity extends AppCompatActivity {
    EditText input_old_password, input_new_password,input_confirm_password;
    TextView forgot_pswd;
    Button submit_pswd;
    String oldPassword, newPassword;
    String confirmPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pswd);
        input_old_password = findViewById(R.id.input_old_password);
        input_new_password = findViewById(R.id.input_new_password);
        input_confirm_password = findViewById(R.id.input_confirm_password);
        submit_pswd = findViewById(R.id.submit_pswd);
        forgot_pswd = findViewById(R.id.forgot_pswd);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        forgot_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePswdActivity.this, ForgotPasswordActivity.class));
            }
        });

            submit_pswd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oldPassword = input_old_password.getText().toString();
                    newPassword = input_new_password.getText().toString();
                    confirmPassword = input_confirm_password.getText().toString();
                    if(TextUtils.isEmpty(oldPassword)||TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(confirmPassword)){
                        if(TextUtils.isEmpty(oldPassword)){
                            input_old_password.setError("*Required");
                        }
                        if(TextUtils.isEmpty(newPassword)){
                            input_new_password.setError("*Required");
                        }
                    }
                    else{
                        if(user!=null) {
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(Objects.requireNonNull(user.getEmail()), oldPassword);
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                if (newPassword.equals(confirmPassword)) {
                                                    user.updatePassword(newPassword);
                                                    Toast.makeText(ChangePswdActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    input_new_password.setError("Passwords mismatched");
                                                    input_confirm_password.setError("Passwords mismatched");
                                                }
                                            } else {
                                                input_old_password.setError("Wrong password");
                                            }
                                        }
                                    });

                        }
                    }

            }

        });
    }
}