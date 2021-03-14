package com.example.stockpot.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockpot.LoginActivity;
import com.example.stockpot.R;
import com.example.stockpot.fragments.DetailFragment;
import com.example.stockpot.models.Stock;

import org.parceler.Parcels;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder>{
    public static final String TAG = "StockAdapter";

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

        if(position % 2 == 0)
        {
            //holder.rootView.setBackgroundColor(Color.BLACK);
            holder.container.setBackgroundResource(R.color.primary);
        }
        else
        {
            //holder.rootView.setBackgroundColor(Color.WHITE);
            holder.container.setBackgroundResource(R.color.secondary);
        }
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        private TextView tvTicker;
        private TextView tvName;
        private TextView tvPrice;

        // RelativeLayout rootView;//newly added field

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            // rootView = (RelativeLayout) itemView.findViewById(R.id.container);

            tvTicker = itemView.findViewById(R.id.tvTicker);
            tvName = itemView.findViewById(R.id.tvSymbol);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            container = itemView.findViewById(R.id.container);
        }



        public void bind(Stock stock) {
            tvTicker.setText(stock.getName());
            tvName.setText(stock.getTickerSym());
            tvPrice.setText(stock.getPrice());

            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // public class ChangeFragment extends Fragment{
                    //     FragmentManager fm = getFragmentManager();
                    //     FragmentTransaction ft = fm.beginTransaction();
                        // FragmentGreen llf = new FragmentGreen();
                        // ft.replace(R.id.listFragment, DetailFragment);
                        // ft.commit();
                        /*
                        getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, mFragment)
                            .commit();
                        */

                        // 2. Navigate to a new activity on tap
                        // Intent i = new Intent(context, LoginActivity.class);
                        // i.putExtra("title", movie.getTitle());
                    // i.putExtra("movie", Parcels.wrap(stocks));
                    // context.startActivity(i);
                        // Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }


                //}
            });


        }
    }
}
