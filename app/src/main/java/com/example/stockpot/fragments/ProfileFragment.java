package com.example.stockpot.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.stockpot.LoginActivity;
import com.example.stockpot.R;
import com.example.stockpot.adapters.StockAdapter;
import com.example.stockpot.models.Stock;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    private ParseUser user;
    private List<String> tickerList;
    private List<Stock> stockList;

    private RecyclerView rvStocks;

    private StockAdapter adapter;

    // API Calls with custom ticker symbol (keywords=%s in this case)
    public String API_URL = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=SECJ8ZBVSYIVN2SK";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Log.i(TAG, "id = logout button");
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = ParseUser.getCurrentUser();
        tickerList = user.getList("stocks");
        Log.i(TAG, user.toString());
        rvStocks = (RecyclerView) view.findViewById(R.id.rvStocks);

        stockList = new ArrayList<>();
        adapter = new StockAdapter(getContext(), stockList);
        rvStocks.setAdapter(adapter);

        rvStocks.setLayoutManager(new LinearLayoutManager(getContext()));

        queryStocks();
    }

    private void queryStocks() {
        for (int i = 0; i < tickerList.size(); i++) {
            AsyncHttpClient client = new AsyncHttpClient();
            // Sends a request to the AlphaVantage API through CodePath's AsyncHttpClient library
            client.get(String.format(API_URL, tickerList.get(i)), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    // Debug message to view if we entered the function successfully
                    Log.d(TAG,"onSuccess");

                    JSONObject jsonObject = json.jsonObject;

                    try {
                        // Holds the JSON results matching the search query (from the Search Endpoint in the API)
                        // where bestMatches is the JSON object returned
                        JSONArray bestMatches = jsonObject.getJSONArray("bestMatches");
                        // Add first result (best match) from the JSON objects into the List of Stocks (stockList)
                        stockList.add(new Stock(bestMatches.getJSONObject(0)));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e(TAG, "Hit json exception", e);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                    Log.d(TAG, "onFailure");
                }
            });
        }
    }

    private void logoutUser() {
        // Log the user out through Parse
        ParseUser.logOut();

        // Sends the user back to the Login screen
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
}