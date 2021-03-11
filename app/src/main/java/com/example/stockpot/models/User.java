package com.example.stockpot.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("User")
public class User extends ParseObject {
    public static String KEY_STOCKS = "stocks";

    public List<String> getStocks(){
        return getList(KEY_STOCKS);
    }
    public void setStocks(String stocks){
        add(KEY_STOCKS, stocks);
    }

}
