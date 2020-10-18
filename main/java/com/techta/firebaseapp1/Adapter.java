package com.techta.firebaseapp1;

import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Adapter {

    FirebaseAuth auth;
    private static FirebaseUser user;
    private Context m_context;
    private UserAdapter userAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<UserModel> userModels, List<String> keys) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        m_context = context;
        userAdapter = new UserAdapter(userModels, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(m_context));
        recyclerView.setAdapter(userAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, age, zip, address;
        private String key;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTxt);
            age = itemView.findViewById(R.id.ageTxt);
            zip = itemView.findViewById(R.id.zipTxt);
            address = itemView.findViewById(R.id.addressTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user != null) {
                        Intent intent = new Intent(m_context, EditUserActivity.class);
                        intent.putExtra("key", key);
                        intent.putExtra("name", name.getText().toString());
                        intent.putExtra("age", age.getText().toString());
                        intent.putExtra("address", address.getText().toString());
                        intent.putExtra("zip", zip.getText().toString());

                        m_context.startActivity(intent);
                    } else {
                        m_context.startActivity(new Intent(m_context, LogInActivity.class));
                    }

                }
            });
        }

        public void bind(UserModel userModel, String key) {
            name.setText(userModel.getName());
            age.setText(String.valueOf(userModel.getAge()));
            zip.setText(String.valueOf(userModel.getZip_code()));
            address.setText(userModel.getAddress());
            this.key = key;
        }
    }
    class UserAdapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<UserModel> userModels;
        private List<String> keys;

        public UserAdapter(List<UserModel> userModels, List<String> keys) {
            this.userModels = userModels;
            this.keys = keys;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(userModels.get(position), keys.get(position));
        }

        @Override
        public int getItemCount() {
            return userModels.size();
        }
    }

    public static void logout() {
        user = null;
    }
}
