package com.example.stockpot;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.stockpot.models.Stock;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class StockDetailActivity extends AppCompatActivity {
    // Debugging purposes
    public static final String TAG = "StockDetailActivity";

    private final String API_URL = "https://www.alphavantage.co/query?function=%s&symbol=%s&apikey=SECJ8ZBVSYIVN2SK";

    String symbol;
    Stock quoteStock;
    Stock overviewStock;

    TextView tvSymbol;
    TextView tvName;
    TextView tvDescription;

    TextView tvOpen;
    TextView tvHigh;
    TextView tvLow;
    TextView tvWkhigh;
    TextView tvWklow;
    TextView tvChange;
    TextView tvChangePercent;
    TextView tvPrice;
    TextView tvVolume;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockdetail);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        tvSymbol = findViewById(R.id.tvSymbol);
        tvName = findViewById(R.id.tvName);

        tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());

        tvOpen = findViewById(R.id.tvOpen);
        tvHigh = findViewById(R.id.tvHigh);
        tvLow = findViewById(R.id.tvLow);
        tvWkhigh = findViewById(R.id.tvWkhigh);
        tvWklow = findViewById(R.id.tvWkLow);
        tvChange = findViewById(R.id.tvChange);
        tvChangePercent = findViewById(R.id.tvChangePercent);
        tvPrice = findViewById(R.id.tvPrice);
        tvVolume = findViewById(R.id.tvVolume);

        displayStockDetails();
    }

    private void displayStockDetails() {
        Stock stock = (Stock) Parcels.unwrap(getIntent().getParcelableExtra("stock"));
        symbol = stock.getTickerSym();

        getQuoteStock();
        getOverviewStock();

        tvSymbol.setText(stock.getTickerSym());
        tvName.setText(stock.getName());

        Toast.makeText(StockDetailActivity.this, stock.getName(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, stock.getName());
    }

    private void displayQuoteInfo() {
        tvOpen.setText(quoteStock.getOpen());
        tvHigh.setText(quoteStock.getHigh());
        tvLow.setText(quoteStock.getLow());
        tvChange.setText(quoteStock.getChange());
        tvChangePercent.setText(quoteStock.getChangePercent());
        tvPrice.setText(quoteStock.getPrice());
        tvVolume.setText(quoteStock.getVolume());
    }

    private void displayOverviewInfo() {
        tvDescription.setText(overviewStock.getDescription());
        tvWkhigh.setText(overviewStock.getWkHigh());
        tvWklow.setText(overviewStock.getWkLow());
    }

    private void getOverviewStock() {
        AsyncHttpClient client = new AsyncHttpClient();
        // Sends a request to the AlphaVantage API through CodePath's AsyncHttpClient library
        client.get(String.format(API_URL, "OVERVIEW", symbol), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                // Debug message to view if we entered the function successfully
                Log.d(TAG,"onSuccess");

                try {
                    JSONObject jsonObject = json.jsonObject;
                    overviewStock = new Stock(jsonObject);
                    displayOverviewInfo();
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

    private void getQuoteStock() {
        AsyncHttpClient client = new AsyncHttpClient();

        // Sends a request to the AlphaVantage API through CodePath's AsyncHttpClient library
        client.get(String.format(API_URL, "GLOBAL_QUOTE", symbol), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                // Debug message to view if we entered the function successfully
                try {
                    JSONObject jsonObject = json.jsonObject;
                    JSONObject quote = jsonObject.getJSONObject("Global Quote");
                    quoteStock = new Stock(quote);
                    displayQuoteInfo();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure" + " " + s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

}
