package com.techta.firebaseapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditUserActivity extends AppCompatActivity {

    private EditText nameET, ageET, zipET, addressET;
    private String key, name, age, zip, address;
    private Button updateUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteUser:
                new FirebaseDatabaseHelper().deleteUser(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserModel> userModels, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(EditUserActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        getSupportActionBar().setTitle("");

        key = getIntent().getStringExtra("key");
        age = getIntent().getStringExtra("age");
        name = getIntent().getStringExtra("name");
        zip = getIntent().getStringExtra("zip");
        address = getIntent().getStringExtra("address");

        nameET = findViewById(R.id.nameET);
        ageET = findViewById(R.id.ageET);
        addressET = findViewById(R.id.addressET);
        zipET = findViewById(R.id.zipET);
        updateUser = findViewById(R.id.updateButton);

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setName(nameET.getText().toString());
                userModel.setAge(Integer.parseInt(ageET.getText().toString()));
                userModel.setZip_code(Long.parseLong(zipET.getText().toString()));
                userModel.setAddress(addressET.getText().toString());

                new FirebaseDatabaseHelper().updateUser(key, userModel, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<UserModel> userModels, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(EditUserActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
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