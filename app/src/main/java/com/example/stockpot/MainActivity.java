package com.example.stockpot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.stockpot.fragments.HomeFragment;
import com.example.stockpot.fragments.MessagesFragment;
import com.example.stockpot.fragments.ComposeFragment;
import com.example.stockpot.fragments.ProfileFragment;
import com.example.stockpot.fragments.SearchFragment;
import com.example.stockpot.models.Stock;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String API_URL = "API HERE";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    List<Stock> stocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        // Toolbar located below every View
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Handles switching between screens (above bottomNavigationView)
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
                        fragment = new SearchFragment(MainActivity.this);
                        break;
                    case R.id.actionCompose:
                        fragment = new ComposeFragment();
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
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
}