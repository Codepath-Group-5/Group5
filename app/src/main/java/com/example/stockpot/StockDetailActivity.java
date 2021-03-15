package com.example.stockpot;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stockpot.models.Stock;

import org.parceler.Parcels;

public class StockDetailActivity extends AppCompatActivity {
    // Debugging purposes
    public static final String TAG = "StockDetailActivity";

    TextView tvSymbol;
    TextView tvName;
    TextView tvDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockdetail);

        tvSymbol = findViewById(R.id.tvSymbol);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        Log.i(TAG, "Test");
        displayStockDetails();

    }

    public void displayStockDetails() {
        Log.i("StockDetailActivity", "displayStockDetails");
        Stock stock = (Stock) Parcels.unwrap(getIntent().getParcelableExtra("stock"));

        tvSymbol.setText(stock.getTickerSym());
        // tvName.setText(stock.getName());
        // tvDescription.setText(stock.getDescription());
        Toast.makeText(StockDetailActivity.this, stock.getName(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, stock.getName());
    }

}
