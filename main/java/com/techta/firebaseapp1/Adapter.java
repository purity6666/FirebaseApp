package com.techta.firebaseapp1;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter {

    private Context m_context;
    private UserAdapter userAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<UserModel> userModels, List<String> keys) {
        m_context = context;
        userAdapter = new UserAdapter(userModels, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(m_context));
        recyclerView.setAdapter(userAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, age, zip, address;
        private String key;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTxt);
            age = itemView.findViewById(R.id.ageTxt);
            zip = itemView.findViewById(R.id.zipTxt);
            address = itemView.findViewById(R.id.addressTxt);
        }

        public void bind(UserModel userModel, String key) {
            name.setText(userModel.getName());
            age.setText(userModel.getAge());
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
            return new ViewHolder(parent);
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
}
