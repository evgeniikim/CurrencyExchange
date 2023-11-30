package repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.DataHelper;
import exception.ExceptionHandling;
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

    @Override
    public int saveToFile() {
        try {
            DataHelper.exportData("currencies.dat", currencies);
            DataHelper.exportData("currencyrates.dat", currencyRates);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            var loadCurrencies = (Map<String, ICurrencyModel>) DataHelper.importData("currencies.dat");
            var loadCurrencyRates = (Map<String, ICurrencyRateModel>) DataHelper.importData("currencyrates.dat");
            currencies = loadCurrencies;
            currencyRates = loadCurrencyRates;
            return 0;
        } catch (IOException | ClassNotFoundException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }
}
