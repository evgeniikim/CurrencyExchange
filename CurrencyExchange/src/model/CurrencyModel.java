package model;

import interfaces.*;
public class CurrencyModel implements ICurrencyModel {
    // Поля класса
    private String currencyCode;
    private String name;

    public CurrencyModel(String currencyCode, String name) {
        this.currencyCode = currencyCode;
        this.name = name;
    }

    @Override
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "currencyCode='" + currencyCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
