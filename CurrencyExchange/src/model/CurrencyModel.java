package model;

import interfaces.*;

import java.io.Serializable;

public class CurrencyModel implements ICurrencyModel, Serializable {
    private static final long serialVersionUID = 1L;
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
    public String getCode() {
        return currencyCode;
    }


    @Override
    public String toString() {
        return "CurrencyModel{" +
                "currencyCode='" + currencyCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
