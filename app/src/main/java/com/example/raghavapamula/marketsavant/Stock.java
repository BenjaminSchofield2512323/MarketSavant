package com.example.raghavapamula.marketsavant;

/**
 * Created by raghavapamula on 12/29/16.
 */
import android.graphics.drawable.Drawable;

public class Stock

{
    private Drawable priceChart;

    public String getPriceChangeDirection() {

        if(Change1h.length() > 0) {

            String d = Change1h.substring(0, 1);

            if (d.equals("-"))
            {
                return "-1";
            }
            else if (d.equals("+"))
            {
                return "1";
            }

        }
        return "0";

    }



    // Getters and setters

    public Drawable getPriceChart() {
        return priceChart;
    }

    public void setPriceChart(Drawable priceChart) {
        this.priceChart = priceChart;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String Rank) { this.Rank = Rank;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public String getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(String MarketCap) {
        this.MarketCap = MarketCap;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailableSupply() {
        return AvailableSupply;
    }

    public void setAvailableSupply(String AvailableSupply) {
        this.AvailableSupply = AvailableSupply;
    }

    public String getVolume24() {
        return Volume24;
    }

    public void setVolume24(String Volume24) {
        this.Volume24 = Volume24;
    }

    public String getChange1h() {
        return Change1h;
    }

    public void setChange1h(String Change1h) {
        this.Change1h = Change1h;
    }

    public String getChange7h() {
        return Change7h;
    }

    public void setChange7h(String Change7h) {
        this.Change7h = Change7h;
    }

    public String getChange7d() {
        return Change7d;
    }

    public void setChange7d(String Change7d) {
        this.Change7d = Change7d;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    private String Rank;
    private String Name;
    private String Symbol;
    private String MarketCap;
    public String price;
    private String Volume24;
    private String Change1h;
    private String Change7h;
    private String Change7d;
    private String TimeStamp;
    private String AvailableSupply;

}