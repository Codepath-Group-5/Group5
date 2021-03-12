package com.example.stockpot.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stock {
    public static final String DEFAULTVAL = "";
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
        /*  Search endpoint */
        try{
            tickerSym = jsonObject.getString("1. symbol");
            name = jsonObject.getString("2. name");
        }
        catch(Exception e){
            tickerSym = DEFAULTVAL;
            name = DEFAULTVAL;
        }


        /*  Company Overview    */
        try{
            tickerSym = jsonObject.getString("Symbol");
            name = jsonObject.getString("Name");
            description = jsonObject.getString("Description");
        }
        catch(Exception e){
            description = DEFAULTVAL;
        }


        /*  Quote Endpoint  */
        try{
            tickerSym = jsonObject.getString("01. symbol");
            open = jsonObject.getString("02. open");
            high = jsonObject.getString("03. high");
            price = jsonObject.getString("05. price");
            volume = jsonObject.getString("06. volume");
            previousClose = jsonObject.getString("08. previous close");
            change = jsonObject.getString("09. change");
            changePercent = jsonObject.getString("10. change percent");
        }
        catch(Exception e){
            open = DEFAULTVAL;
            high = DEFAULTVAL;
            price = DEFAULTVAL;
            volume = DEFAULTVAL;
            previousClose = DEFAULTVAL;
            change = DEFAULTVAL;
            changePercent = DEFAULTVAL;
        }

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

    public String getTickerSym() {
        return tickerSym;
    }

    public String getDescription() {
        return description;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public String getChange() {
        return change;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTickerSym(String tickerSym) {
        this.tickerSym = tickerSym;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }
}
