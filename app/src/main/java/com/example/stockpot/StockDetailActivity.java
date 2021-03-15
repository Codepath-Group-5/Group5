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

    TextView open;
    TextView high;
    TextView low;
    TextView wkhigh52;
    TextView wklow52;
    TextView tvOpen;
    TextView tvHigh;
    TextView tvLow;
    TextView tvWkhigh;
    TextView tvWklow;
    TextView volume;
    TextView change;
    TextView changepercent;
    TextView tvPrice;
    TextView tvVolume;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockdetail);

        tvSymbol = findViewById(R.id.tvSymbol);
        tvName = findViewById(R.id.tvName);

        // Not working for some reason
        tvDescription = findViewById(R.id.tvDescription);

        open = findViewById(R.id.open);
        high = findViewById(R.id.high);
        low = findViewById(R.id.low);
        wkhigh52 = findViewById(R.id.wkhigh52);
        wklow52 = findViewById(R.id.wklow52);
        tvOpen = findViewById(R.id.tvOpen);
        tvHigh = findViewById(R.id.tvHigh);
        tvLow = findViewById(R.id.tvLow);
        tvWkhigh = findViewById(R.id.tvWkhigh);
        // tvWklow = findViewById(R.id.tvWklow);
        volume = findViewById(R.id.volume);
        change = findViewById(R.id.changepercent);
        changepercent = findViewById(R.id.changepercent);
        tvPrice = findViewById(R.id.tvPrice);
        tvVolume = findViewById(R.id.tvVolume);



        displayStockDetails();
    }

    public void displayStockDetails() {
        Log.i("StockDetailActivity", "displayStockDetails");
        Stock stock = (Stock) Parcels.unwrap(getIntent().getParcelableExtra("stock"));

        tvSymbol.setText(stock.getTickerSym());
        tvName.setText(stock.getName());
        // tvDescription.setText(stock.getDescription());

        open.setText(stock.getOpen());
        high.setText(stock.getHigh());
        // low.setText(stock.getLow());
        // wkhigh52.setText(stock.ge);
        // wklow52.setText();
        tvOpen.setText(stock.getOpen());
        tvHigh.setText(stock.getHigh());
        // tvLow.setText(stock.getLow());
        // tvWkhigh.setText();
        // tvWklow.setText();
        volume.setText(stock.getVolume());
        change.setText(stock.getChange());
        changepercent.setText(stock.getChangePercent());
        tvPrice.setText(stock.getPrice());
        tvVolume.setText(stock.getVolume());

        Toast.makeText(StockDetailActivity.this, stock.getName(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, stock.getName());
    }

}
