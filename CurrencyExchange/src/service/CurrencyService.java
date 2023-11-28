package service;

import java.util.List;
import interfaces.*;

public class CurrencyService implements ICurrencyService {

    private ICurrencyRepository currencyRepository; // Репозиторий для работы с валютами

    public CurrencyService(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<ICurrencyModel> getAllCurrencies() {
        return currencyRepository.findAllCurrencies();
    }

    @Override
    public ICurrencyRateModel getCurrencyRate(String currencyCode) {
        return currencyRepository.findCurrencyRate(currencyCode);
    }

    @Override
    public void updateCurrencyRate(ICurrencyRateModel currencyRate) {
        currencyRepository.addCurrencyRate(currencyRate);
        System.out.println("Курс валюты обновлен: " + currencyRate);
    }
}
