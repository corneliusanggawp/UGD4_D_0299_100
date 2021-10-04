package com.example.ugd4_d_0299_100;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ugd4_d_0299_100.Model.User;
import com.example.ugd4_d_0299_100.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

public class MainFragment extends Fragment {
    private TextView tvWelcome;
    private MaterialButton btnLogout;
    private User user;
    private UserPreferences userPreferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreferences = new UserPreferences(this.getContext());
        tvWelcome = view.findViewById(R.id.tvWelcome);
        btnLogout = view.findViewById(R.id.btnLogout);
        user = userPreferences.getUserLogin();

        checkLogin();
        tvWelcome.setText("Selamat datang, " + user.getUsername());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                Toast.makeText(MainFragment.this.getContext(), "Bye Bye", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        if (!userPreferences.checkLogin()) {
            startActivity(new Intent(MainFragment.this.getContext(), LoginActivity.class));
            getActivity().finish();
        } else {
            Toast.makeText(MainFragment.this.getContext(), "Selamat Datang Kembali", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
