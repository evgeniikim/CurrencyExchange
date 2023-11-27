package interfaces;

import java.util.List;

public interface ICurrencyHistoryService {
    List<ICurrencyRateModel> getHistoricalRates(String currencyCode);
    void saveHistoricalRate(ICurrencyRateModel currencyRate);
}
