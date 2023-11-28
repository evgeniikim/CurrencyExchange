package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import interfaces.*;

public class CurrencyRepository implements ICurrencyRepository {
    private Map<String, ICurrencyModel> currencies; // Хранилище данных о валютах
    private Map<String, ICurrencyRateModel> currencyRates; // Хранилище данных о курсах валют

    public CurrencyRepository() {
        this.currencies = new HashMap<>();
        this.currencyRates = new HashMap<>();
    }

    @Override
    public List<ICurrencyModel> findAllCurrencies() {
        return new ArrayList<>(currencies.values());
    }

    @Override
    public ICurrencyRateModel findCurrencyRate(String currencyCode) {
        return currencyRates.get(currencyCode);
    }

    @Override
    public void addCurrencyRate(ICurrencyRateModel currencyRate) {
        if (currencyRate != null) {
            currencyRates.put(currencyRate.getCurrencyCode(), currencyRate);
            System.out.println("Курс валюты сохранен: " + currencyRate);
        }
    }
}
