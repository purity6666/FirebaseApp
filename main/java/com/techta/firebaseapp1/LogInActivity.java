package com.techta.firebaseapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginButton, registerButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        auth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        progressBar = findViewById(R.id.progressBarLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty()) return;
                inProgress(true);
                auth.signInWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LogInActivity.this, "User Signed in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish(); return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(LogInActivity.this, "Oops, something went wrong!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty()) return;
                inProgress(true);
                auth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LogInActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        inProgress(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(LogInActivity.this, "Oops, something went wrong!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    private void inProgress(boolean x) {
        if (x) {
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);
        }
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(emailET.getText().toString())) {
            emailET.setError("Required!");
            return true;
        }
        if (TextUtils.isEmpty(passwordET.getText().toString())){
            passwordET.setError("Required!");
            return true;
        }
        return false;
    }
}