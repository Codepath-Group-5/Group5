package com.example.stockpot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.stockpot.fragments.HomeFragment;
import com.example.stockpot.fragments.MessagesFragment;
import com.example.stockpot.fragments.NotificationsFragment;
import com.example.stockpot.fragments.ProfileFragment;
import com.example.stockpot.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.actionHome:
                        fragment = new HomeFragment();
                        break;
                    case R.id.actionSearch:
                        fragment = new SearchFragment();
                        break;
                    case R.id.actionMessages:
                        fragment = new MessagesFragment();
                        break;
                    case R.id.actionNotifications:
                        fragment = new NotificationsFragment();
                        break;
                    case R.id.actionProfile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.actionHome);
    }
}