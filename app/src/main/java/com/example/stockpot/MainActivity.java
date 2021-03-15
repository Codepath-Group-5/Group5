package com.example.stockpot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.stockpot.adapters.StockAdapter;
import com.example.stockpot.fragments.HomeFragment;
import com.example.stockpot.fragments.MessagesFragment;
import com.example.stockpot.fragments.NotificationsFragment;
import com.example.stockpot.fragments.ProfileFragment;
import com.example.stockpot.fragments.SearchFragment;
import com.example.stockpot.models.Stock;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


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

        /*stocks = new ArrayList<>();
        //Create Adapter
        StockAdapter movieAdapter = new StockAdapter(this, stocks);
        //Set the Adapter on Recycler View
        rvStocks.setAdapter(movieAdapter);
        //Set Layout manager on Recycler View
        rvStocks.setLayoutManager(new LinearLayoutManager(this));*/

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

       /* AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    stocks.addAll(Stock.fromJsonArray(results));
                    //stockAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Results: " + stocks.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });*/
    }
}