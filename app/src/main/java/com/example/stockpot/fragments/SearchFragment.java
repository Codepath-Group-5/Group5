package com.example.stockpot.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.stockpot.R;
import com.example.stockpot.adapters.StockAdapter;
import com.example.stockpot.models.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchFragment extends Fragment {
    public static final String TAG = "Search Fragment";
    private RecyclerView rvStocks;
    private EditText etText;
    private ImageButton btnSearch;
    protected StockAdapter adapter;
    protected List<Stock> allStocks;

    public String API_URL = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=SECJ8ZBVSYIVN2SK";
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvStocks = view.findViewById(R.id.rvStocks);
        etText = view.findViewById(R.id.etText);
        btnSearch = view.findViewById(R.id.btnSearch);

        allStocks = new ArrayList<>();
        adapter = new StockAdapter(getContext(),allStocks);
        rvStocks.setAdapter(adapter);
        rvStocks.setLayoutManager(new LinearLayoutManager(getContext()));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allStocks.clear();
                String keyWord = etText.getText().toString();
                Toast.makeText(getContext(), "IT WORKED", Toast.LENGTH_SHORT).show();
                queryStocks(keyWord);
            }
        });
    }
    protected void queryStocks(String keyWord){

        AsyncHttpClient client = new AsyncHttpClient();
        Log.e(TAG, "THIS IS URL" + String.format(API_URL,keyWord));
        client.get(String.format(API_URL,keyWord), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray bestMatches = jsonObject.getJSONArray("bestMatches");
                    Log.i(TAG, "bestMatches: " + bestMatches.toString());
                    allStocks.addAll(Stock.fromJsonArray(bestMatches));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "bestMatches: " + allStocks.size());
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