package com.example.stockpot;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.stockpot.models.Stock;

import org.json.JSONArray;
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
        Log.i("StockDetailActivity", "displayStockDetails");
        Stock stock = (Stock) Parcels.unwrap(getIntent().getParcelableExtra("stock"));
        symbol = stock.getTickerSym();

        getQuoteStock();
        //getOverviewStock();

        tvSymbol.setText(stock.getTickerSym());
        tvName.setText(stock.getName());
        //tvDescription.setText(overviewStock.getDescription());

        tvOpen.setText(quoteStock.getOpen());
        tvHigh.setText(quoteStock.getHigh());
        tvLow.setText(quoteStock.getLow());
        //tvWkhigh.setText(overviewStock.getWkHigh());
        //tvWklow.setText(overviewStock.getWkLow());
        tvChange.setText(quoteStock.getChange());
        tvChangePercent.setText(quoteStock.getChangePercent());
        tvPrice.setText(quoteStock.getPrice());
        tvVolume.setText(quoteStock.getVolume());

        Toast.makeText(StockDetailActivity.this, stock.getName(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, stock.getName());
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
        Log.i(TAG, String.format(API_URL, "GLOBAL_QUOTE", symbol));
        client.get(String.format(API_URL, "GLOBAL_QUOTE", symbol), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                // Debug message to view if we entered the function successfully
                Log.d(TAG,"onSuccess");
                try {
                    JSONObject jsonObject = json.jsonObject;
                    JSONObject quote = jsonObject.getJSONObject("Global Quote");
                    quoteStock = new Stock(quote);
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
