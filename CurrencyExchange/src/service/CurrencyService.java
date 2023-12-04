package service;

import java.util.List;

import interfaces.*;
import model.CurrencyModel;

public class CurrencyService implements ICurrencyService {

    private final ICurrencyRepository currencyRepository; // Репозиторий для работы с валютами

    // Конструктор, принимающий репозиторий валют
    public CurrencyService(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    // Метод для получения списка всех валют
    @Override
    public List<ICurrencyModel> getAllCurrencies() {
        return currencyRepository.findAllCurrencies();
    }

    // Метод для получения курса валюты по её коду
    @Override
    public ICurrencyRateModel getCurrencyRate(String currencyCode) {
        return currencyRepository.findCurrencyRate(currencyCode);
    }

    // Метод для обновления курса валюты
    @Override
    public void updateCurrencyRate(ICurrencyRateModel currencyRate) {
        currencyRepository.addCurrencyRate(currencyRate);
        System.out.println("Курс валюты обновлен: " + currencyRate);
    }

    // Метод для удаления валюты по её коду
    public void removeCurrency(String currencyCode) {
        currencyRepository.removeCurrency(currencyCode);
    }

    // Метод для добавления новой валюты
    @Override
    public void addCurrency(ICurrencyModel currency) {
        currencyRepository.addCurrency(currency);
    }

    @Override
    public void loadData() {
        currencyRepository.loadFromFile();
    }
    @Override
    public void saveData() {
        currencyRepository.saveToFile();
    }
}
