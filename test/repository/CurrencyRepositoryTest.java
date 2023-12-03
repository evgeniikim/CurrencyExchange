package repository;

import interfaces.ICurrencyModel;
import interfaces.ICurrencyRateModel;
import model.CurrencyModel;
import model.CurrencyRateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CurrencyRepositoryTest {
    private static final String CURRENCIES_FILE = "currencies_test.dat";
    private static final String CURRENCY_RATES_FILE = "currencyrates_test.dat";
    private CurrencyRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CurrencyRepository();
    }

    @Test
    void findAllCurrencies() {
        CurrencyRepository repository = new CurrencyRepository();
        List<ICurrencyModel> allCurrencies = repository.findAllCurrencies();
        // Проверяем, что список не пуст
        assertFalse(((List<?>) allCurrencies).isEmpty());
        // Проверяем, что все валюты изначально добавлены
        assertEquals(3, allCurrencies.size());
    }

    @Test
    void findCurrencyRate() {
        CurrencyRepository repository = new CurrencyRepository();
        ICurrencyRateModel rate = new CurrencyRateModel("USD", 1.0, new Date());
        repository.addCurrencyRate(rate);
        ICurrencyRateModel foundRate = repository.findCurrencyRate("USD");
        assertNotNull(foundRate);
        assertEquals(rate.getCurrencyCode(), foundRate.getCurrencyCode());
        //assertEquals(rate.getExchangeRate(), foundRate.getExchangeRate());
    }

    @Test
    void addCurrencyRate() {
        CurrencyRepository repository = new CurrencyRepository();
        ICurrencyRateModel rate = new CurrencyRateModel("USD", 1.0, new Date());
        repository.addCurrencyRate(rate);
        ICurrencyRateModel addedRate = repository.findCurrencyRate("USD");
        assertNotNull(addedRate);
        assertEquals(rate.getCurrencyCode(), addedRate.getCurrencyCode());
    }

    @Test
    void removeCurrency() {
        CurrencyRepository repository = new CurrencyRepository();
        String currencyCode = "USD";
        ICurrencyRateModel rate = new CurrencyRateModel(currencyCode, 1.0, new Date());
        repository.addCurrencyRate(rate);
        repository.removeCurrency(currencyCode);
        assertNull(repository.findCurrencyRate(currencyCode));
    }

    @Test
    void saveAndLoadFromFile() {
        CurrencyRepository repository = new CurrencyRepository();
        // Сохраняем в файл
        int saveResult = repository.saveToFile();
        assertEquals(0, saveResult);
        // Очищаем репозиторий
        repository = new CurrencyRepository();
        // Загружаем из файла
        int loadResult = repository.loadFromFile();
        assertEquals(0, loadResult);
    }

  /* @Test
    void addCurrency() {
        CurrencyRepository repository = new CurrencyRepository();
        ICurrencyModel currency = new CurrencyModel("JPY", "Japanese Yen");

        repository.addCurrency(currency);

        ICurrencyModel addedCurrency = repository.findCurrencyByCode("JPY");
        assertNotNull(addedCurrency);
        assertEquals(currency.getCurrencyCode(), addedCurrency.getCurrencyCode());
        //assertEquals(currency.getCurrencyName(), addedCurrency.getCurrencyName());
    }
*/

    @Test
    void saveToFile() {
        repository.addCurrency(new CurrencyModel("GBP", "British Pound"));
        repository.addCurrency(new CurrencyModel("JPY", "Japanese Yen"));
        repository.addCurrencyRate(new CurrencyRateModel("GBP", 0.75, new Date()));
        int result = repository.saveToFile();
        assertEquals(0, result);
    }

    @Test
    void loadFromFile() {
        // Save some test data to files
        repository.addCurrency(new CurrencyModel("GBP", "British Pound"));
        repository.addCurrencyRate(new CurrencyRateModel("GBP", 0.75, new Date()));
        repository.saveToFile();
        repository = new CurrencyRepository();
        int result = repository.loadFromFile();
        assertEquals(0, result);
        assertTrue(repository.findAllCurrencies().size() > 0);
        assertTrue(repository.findCurrencyRate("GBP") != null);
    }
}