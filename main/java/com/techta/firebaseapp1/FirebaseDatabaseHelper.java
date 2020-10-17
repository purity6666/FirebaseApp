package com.techta.firebaseapp1;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private List<UserModel> userModels = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<UserModel> userModels, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
    }

    public void readUsers(final DataStatus dataStatus) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    UserModel userModel = keyNode.getValue(UserModel.class);
                    userModels.add(userModel);
                }

                dataStatus.DataIsLoaded(userModels, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addUser(UserModel userModel, final DataStatus dataStatus) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void updateUser(String key, UserModel userModel, final DataStatus dataStatus) {
        databaseReference.child(key).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteUser(String key, final DataStatus dataStatus) {
        databaseReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }
}
