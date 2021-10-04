package com.example.ugd4_d_0299_100;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment(new MainFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment nav = null;

        switch (item.getItemId()) {
            case R.id.main:
                nav = new MainFragment();
                break;
            case R.id.list:
                nav = new com.example.ugd4_d_0299_100.TodoFragment();
                break;
        }

        return loadFragment(nav);
    }

    private boolean loadFragment(Fragment nav) {
        if (nav != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, nav).commit();
            return true;
        }
        return false;
    }
}