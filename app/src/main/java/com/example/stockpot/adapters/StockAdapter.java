package com.example.stockpot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockpot.R;
import com.example.stockpot.models.Stock;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder>{
    private Context context;
    private List<Stock> stocks;
    
    public StockAdapter(Context context, List<Stock> stocks){
        this.context = context;
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.bind(stock);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTicker;
        private TextView tvName;
        private TextView tvPrice;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvTicker = itemView.findViewById(R.id.tvTicker);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
        public void bind(Stock stock) {
            tvTicker.setText(stock.getName());
            tvName.setText(stock.getTickerSym());
            tvPrice.setText(stock.getPrice());
        }
    }
}
