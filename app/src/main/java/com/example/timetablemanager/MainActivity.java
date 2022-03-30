package com.example.timetablemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    BottomNavigationView bottom_nav_view;
    FloatingActionButton add_task_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        bottom_nav_view = findViewById(R.id.bottom_nav_view);
        add_task_btn = findViewById(R.id.add_task_btn);

        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        bottom_nav_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.tasks){
                    loadFrag(new TasksFragment(), false);
                }
                else if(id==R.id.postponed){
                    loadFrag(new PostponedFragment(), false);
                }
                else{
                    loadFrag(new StatusFragment(), false);
                }
                return true;
            }
        });

        bottom_nav_view.setSelectedItemId(R.id.tasks);


    }


    public void loadFrag(Fragment fragment, boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag) {
            ft.add(R.id.container, fragment);
        }
        else {
            ft.replace(R.id.container, fragment);
        }
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.create();
            builder.setTitle("Logout");
            builder.setMessage("\nAre you sure you want to log-out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mAuth.signOut();
                            Toast.makeText(MainActivity.this, "Logged-out", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });
            builder.setNegativeButton("No",null);
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(item.getItemId()==R.id.profile){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
            return true;
    }


}