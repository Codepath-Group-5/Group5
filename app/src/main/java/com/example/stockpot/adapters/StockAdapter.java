package com.example.stockpot.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockpot.MainActivity;
import com.example.stockpot.R;
import com.example.stockpot.StockDetailActivity;
import com.example.stockpot.models.Stock;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
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

    // Clean all elements of the recycler
    public void clear() {
        stocks.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        private TextView tvTicker;
        private TextView tvName;
        private TextView tvPrice;
        private ImageButton btnAddToWatchList;
        private CheckBox checkBoxAddToWatchList;

        // RelativeLayout rootView;//newly added field

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            // rootView = (RelativeLayout) itemView.findViewById(R.id.container);

            tvTicker = itemView.findViewById(R.id.tvTicker);
            tvName = itemView.findViewById(R.id.tvSymbol);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            container = itemView.findViewById(R.id.container);
            // btnAddToWatchList = itemView.findViewById(R.id.btnAddToWatchList);
            checkBoxAddToWatchList = itemView.findViewById(R.id.checkBoxAddToWatchList);



            /*
            View.OnClickListener imgButtonHandler = new View.OnClickListener() {

                public void onClick(View v) {
                    btnAddToWatchList.setBackgroundResource(R.drawable.ufi_save);
                }
            };

            btnAddToWatchList.setOnClickListener(imgButtonHandler);

             */
        }



        public void bind(Stock stock) {
            tvTicker.setText(stock.getName());
            tvName.setText(stock.getTickerSym());
            tvPrice.setText(stock.getPrice());




            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    Intent intent = new Intent(context, StockDetailActivity.class);
                    intent.putExtra("stock", Parcels.wrap(stock));

                    Log.i("StockDetailActivty", "Going to StockDetailActivity from StockAdapter");
                    context.startActivity(intent);


                    // public class ChangeFragment extends Fragment{
                    //     FragmentManager fm = getFragmentManager();
                    //     FragmentTransaction ft = fm.beginTransaction();
                        // FragmentGreen llf = new FragmentGreen();
                        // ft.replace(R.id.listFragment, DetailFragment);
                        // ft.commit();
                    /*
                        context.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, mFragment)
                            .commit();
                    */
                        // Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }


                //}
            });

            checkBoxAddToWatchList.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ParseUser user = ParseUser.getCurrentUser();
                    final String KEY = "stocks";
                    String stockSym = stock.getTickerSym();

                    if (checkBoxAddToWatchList.isChecked())
                    {
                        // checkBoxAddToWatchList.setChecked(false);
                        Toast.makeText(context, "Stock followed!", Toast.LENGTH_SHORT).show();

                        user.addUnique(KEY, stockSym);
                        Log.i(TAG, String.format("Adding %s to stocks list", stockSym));
                    }
                    else {
                        // checkBoxAddToWatchList.setChecked(true);
                        Toast.makeText(context, "Stock Unfollowed!", Toast.LENGTH_SHORT).show();

                        List<String> toRemove = new ArrayList<>();
                        toRemove.add(stockSym);
                        user.removeAll(KEY, toRemove);
                    }
                    // Saves the object
                    user.saveInBackground(e -> {
                        if (e == null) {
                            // Save successfull
                            Log.i(TAG, String.format("Successfully saved %s to stocks list", stockSym));
                        } else {
                            // Something went wrong while saving
                            Log.i(TAG, String.format("Failed to save %s to stocks list", stockSym));
                        }
                    });
                }
            });
        }
    }
}
