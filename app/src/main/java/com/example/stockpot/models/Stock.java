package com.example.stockpot.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stock {
    /*  Search endpoint */
    String name;
    String tickerSym;
    /*  Company Overview    */
    String description;
    /*  Quote Endpoint  */
    String open;
    String high;
    String price;
    String volume;
    String previousClose;
    String change;
    String changePercent;

    public Stock(JSONObject jsonObject) throws JSONException {
        /*name = jsonObject.getString("name");
        tickerSym = jsonObject.getString("");

        description = jsonObject.getString("");

        open = jsonObject.getString("");
        high = jsonObject.getString("");
        price = jsonObject.getString("");
        volume = jsonObject.getString("");
        previousClose = jsonObject.getString("");
        change = jsonObject.getString("");
        changePercent = jsonObject.getString("");*/
    }

    public static List<Stock> fromJsonArray(JSONArray stockJsonArray) throws JSONException {
        List<Stock> stocks = new ArrayList<>();
        for(int i = 0; i < stockJsonArray.length(); i++){
            stocks.add(new Stock(stockJsonArray.getJSONObject(i)));
        }
        return stocks;
    }
    /* ALL GET FUNCTIONS HERE */
    public String getName(){
        return name;
    }
}
