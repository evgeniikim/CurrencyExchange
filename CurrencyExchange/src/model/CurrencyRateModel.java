package model;

import java.util.Date;
import interfaces.*;

public class CurrencyRateModel implements ICurrencyRateModel {
    private String currencyCode;
    private double rate;
    private Date date;

    public CurrencyRateModel(String currencyCode, double rate, Date date) {
        this.currencyCode = currencyCode;
        this.rate = rate;
        this.date = date;
    }

    @Override
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CurrencyRateModel{" +
                "currencyCode='" + currencyCode + '\'' +
                ", rate=" + rate +
                ", date=" + date +
                '}';
    }
}
