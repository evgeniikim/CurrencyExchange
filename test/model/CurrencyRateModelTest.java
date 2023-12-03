package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class CurrencyRateModelTest {

    private CurrencyRateModel currencyRate;

    @BeforeEach
    void setUp() {
        // Предполагается, что у вас есть объект Date для передачи в конструктор
        Date currentDate = new Date();
        currencyRate = new CurrencyRateModel("USD", 1.0, currentDate);
    }

    @Test
    void getCurrencyCode() {
        assertEquals("USD", currencyRate.getCurrencyCode());
    }

    @Test
    void getRate() {
        assertEquals(1.0, currencyRate.getRate());
    }

    @Test
    void getDate() {
        assertNotNull(currencyRate.getDate());
    }

    @Test
    void testToString() {
        String expectedString = "CurrencyRateModel{currencyCode='USD', rate=1.0, date=" + currencyRate.getDate() + '}';
        assertEquals(expectedString, currencyRate.toString());
    }
}
