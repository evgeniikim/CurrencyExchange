package repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import Helper.DataHelper;
import com.google.gson.reflect.TypeToken;
import exception.ExceptionHandling;
import interfaces.*;
import model.*;

public class CurrencyRepository implements ICurrencyRepository {
    private Map<String, ICurrencyModel> currencies; // Хранилище данных о валютах
    private Map<String, ICurrencyRateModel> currencyRates; // Хранилище данных о курсах валют

    public CurrencyRepository() {
        this.currencies = new HashMap<>();
        this.currencyRates = new HashMap<>();

        //TODO нужно вынести в DATAHelper
        // Инициализация валют по умолчанию
        addDefaultCurrency(new CurrencyModel("USD", "US Dollar"));
        addDefaultCurrency(new CurrencyModel("EUR", "EU Euro"));
        addDefaultCurrency(new CurrencyModel("UAH", "UH Hryvnia"));

        addDefaultCurrencyRate(new CurrencyRateModel("USD", 1, new Date()));
        addDefaultCurrencyRate(new CurrencyRateModel("EUR", 0.9, new Date()));
        addDefaultCurrencyRate(new CurrencyRateModel("UAH", 0.4,new Date()));
    }

    @Override
    public List<ICurrencyModel> findAllCurrencies() {
        return new ArrayList<>(currencies.values());
    }

    @Override
    public ICurrencyRateModel findCurrencyRate(String currencyCode) {
        return currencyRates.get(currencyCode);
    }

    // Метод для добавления нового курса валюты
    @Override
    public void addCurrencyRate(ICurrencyRateModel currencyRate) {
        if (currencyRate != null) {
            currencyRates.put(currencyRate.getCurrencyCode(), currencyRate);
            System.out.println("Курс валюты сохранен: " + currencyRate);
        }
    }

    // Метод для удаления валюты по её коду
    public void removeCurrency(String currencyCode) {
        currencies.remove(currencyCode);
        // Также удаляем соответствующий курс валюты, если он существует
        currencyRates.remove(currencyCode);
        System.out.println("Валюта удалена: " + currencyCode);
    }

    @Override
    public int saveToFile() {
        try {
            DataHelper.exportDataToJson("currencies.json", currencies);
            DataHelper.exportDataToJson("currencyrates.json", currencyRates);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            Type typeICurrencyModel = new TypeToken<Map<String, CurrencyModel>>(){}.getType();
            Map<String, ICurrencyModel> loadCurrencies = DataHelper.importDataFromJson("currencies.json", typeICurrencyModel);
            if(loadCurrencies!=null) {
                currencies = loadCurrencies;
            }

            Type typeICurrencyRateModel = new TypeToken<Map<String, CurrencyRateModel>>(){}.getType();
            Map<String, ICurrencyRateModel> loadCurrencyRates = DataHelper.importDataFromJson("currencyrates.json", typeICurrencyRateModel);
            if(loadCurrencyRates!=null) {
                currencyRates = loadCurrencyRates;
            }

            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    // Метод для добавления новой валюты
    @Override
    public void addCurrency(ICurrencyModel currency) {
        // Проверяем, не является ли добавляемая валюта одной из трех валют по умолчанию
        if (!isDefaultCurrency(currency)) {
            // Если не является, добавляем валюту
            currencies.put(currency.getCurrencyCode(), currency);
            System.out.println("Новая валюта добавлена: " + currency);
        } else {
            System.out.println("Эта валюта уже существует по умолчанию.");
        }
    }

    // Метод для проверки, является ли валюта одной из трех валют по умолчанию
    private boolean isDefaultCurrency(ICurrencyModel currency) {
        String currencyCode = currency.getCurrencyCode();
        return currencyCode.equals("USD") || currencyCode.equals("EUR") || currencyCode.equals("UAH");
    }

    // Метод для добавления валюты по умолчанию
    private void addDefaultCurrency(ICurrencyModel currency) {
        currencies.put(currency.getCurrencyCode(), currency);
    }
    private void addDefaultCurrencyRate(CurrencyRateModel currencyRate) {
        currencyRates.put(currencyRate.getCurrencyCode(), currencyRate);
    }

}
