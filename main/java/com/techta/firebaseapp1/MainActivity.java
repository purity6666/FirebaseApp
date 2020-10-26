package com.techta.firebaseapp1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private FloatingActionButton addBtn;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user = auth.getCurrentUser();
        getMenuInflater().inflate(R.menu.add_menu, menu);

        if (user != null) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loginRegister:
                startActivity(new Intent(this, LogInActivity.class));
                return true;
            case R.id.logout:
                auth.signOut();
                invalidateOptionsMenu();
                Adapter.logout();
                addBtn.setVisibility(View.GONE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        addBtn = findViewById(R.id.fab);

        if (user == null) {
            addBtn.setVisibility(View.GONE);
        } else {
            addBtn.setVisibility(View.VISIBLE);
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewUser.class);
                startActivity(intent);
                return;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        new FirebaseDatabaseHelper().readUsers(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UserModel> userModels, List<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                new Adapter().setConfig(recyclerView, MainActivity.this, userModels, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


    }
}