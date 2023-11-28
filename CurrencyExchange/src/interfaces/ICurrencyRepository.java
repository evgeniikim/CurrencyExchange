package interfaces;

import java.util.List;

public interface ICurrencyRepository {
    List<ICurrencyModel> findAllCurrencies();
    ICurrencyRateModel findCurrencyRate(String currencyCode);
    void addCurrencyRate(ICurrencyRateModel currencyRate);
}