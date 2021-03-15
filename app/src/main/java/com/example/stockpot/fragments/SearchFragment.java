package com.example.stockpot.fragments;

import android.content.Context;
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

    // Used for Debugging Purposes
    public static final String TAG = "SearchFragment";

    private RecyclerView rvStocks;
    private EditText etText;
    private ImageButton btnSearch;

    protected StockAdapter adapter;
    protected List<Stock> allStocks;

    protected Context currentContext;

    // API Calls with custom ticker symbol (keywords=%s in this case)
    public String API_URL = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=SECJ8ZBVSYIVN2SK";

    public SearchFragment() {
        // Required empty public constructor
    }

    public SearchFragment(Context context) {
        // Required empty public constructor
        currentContext = context;
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

        // Holds the list of results relating to the search query
        allStocks = new ArrayList<>();

        adapter = new StockAdapter(getContext(), allStocks);
        rvStocks.setAdapter(adapter);

        rvStocks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Send a request to API after user presses the search image button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the List of stocks when a new query is requested
                allStocks.clear();
                String keyWord = etText.getText().toString();

                // Toast.makeText(getContext(), "IT WORKED", Toast.LENGTH_SHORT).show();

                queryStocks(keyWord);
            }
        });
    }

    protected void queryStocks(String keyWord){

        AsyncHttpClient client = new AsyncHttpClient();

        // Debugging to view if the URL is accurate
        Log.e(TAG, "THIS IS URL" + String.format(API_URL, keyWord));

        // Sends a request to the AlphaVantage API through CodePath's AsyncHttpClient library
        client.get(String.format(API_URL, keyWord), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                // Debug message to view if we entered the function successfully
                Log.d(TAG,"onSuccess");

                JSONObject jsonObject = json.jsonObject;

                try {
                    // Holds the JSON results matching the search query (from the Search Endpoint in the API)
                    // where bestMatches is the JSON object returned
                    JSONArray bestMatches = jsonObject.getJSONArray("bestMatches");

                    // Debug message to view the JSON objects
                    Log.i(TAG, "bestMatches: " + bestMatches.toString());

                    // Add all the results from the JSON objects into the List of Stocks (allStocks)
                    allStocks.addAll(Stock.fromJsonArray(bestMatches));
                    adapter.notifyDataSetChanged();

                    // Debug message: View the # of stocks returned
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