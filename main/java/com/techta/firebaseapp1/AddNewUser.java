package com.techta.firebaseapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddNewUser extends AppCompatActivity {

    private EditText name, age, zip, address;
    private Button addUser;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.nameET);
        age = findViewById(R.id.ageET);
        address = findViewById(R.id.addressET);
        zip = findViewById(R.id.zipET);
        addUser = findViewById(R.id.button);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setName(name.getText().toString());
                userModel.setAge(Integer.parseInt(age.getText().toString()));
                userModel.setAddress(address.getText().toString());
                userModel.setZip_code(Long.parseLong(zip.getText().toString()));

                new FirebaseDatabaseHelper().addUser(userModel, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserModel> userModels, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(AddNewUser.this, "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

                finish();
                return;
            }
        });
    }
}