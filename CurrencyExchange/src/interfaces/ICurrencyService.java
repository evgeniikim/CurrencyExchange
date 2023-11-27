package interfaces;

import java.util.List;

public interface ICurrencyService {
    List<ICurrencyModel> getAllCurrencies();
    ICurrencyRateModel getCurrencyRate(String currencyCode);
    void updateCurrencyRate(ICurrencyRateModel currencyRate);
}