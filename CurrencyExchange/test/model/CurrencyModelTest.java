package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyModelTest {

    private CurrencyModel currency;

    @BeforeEach
    void setUp() {
        currency = new CurrencyModel("USD", "US Dollar");
    }

    @Test
    void getCurrencyCode() {
        assertEquals("USD", currency.getCurrencyCode());
    }

    @Test
    void getName() {
        assertEquals("US Dollar", currency.getName());
    }

    @Test
    void getCode() {
        assertEquals("USD", currency.getCode());
    }

    @Test
    void testToString() {
        String expectedString = "CurrencyModel{currencyCode='USD', name='US Dollar'}";
        assertEquals(expectedString, currency.toString());
    }
}
