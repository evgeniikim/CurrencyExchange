package model;

import interfaces.ICurrencyModel;
import interfaces.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountModelTest {

    private AccountModel account;

    @BeforeEach
    void setUp() {
        ICurrencyModel currency = new CurrencyModel("USD", "US Dollar");
        account = new AccountModel(1, 1001, currency, 500.0);
    }

    @Test
    void getAccountId() {
        assertEquals(1, account.getAccountId());
    }

    @Test
    void getUserId() {
        assertEquals(1001, account.getUserId());
    }

    @Test
    void getCurrency() {
        ICurrencyModel currency = account.getCurrency();
        assertNotNull(currency);
        assertEquals("USD", currency.getCode());
    }

    @Test
    void getBalance() {
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void setCurrency() {
        ICurrencyModel newCurrency = new CurrencyModel("EUR", "US Dollar");
        account.setCurrency(newCurrency);

        assertEquals(newCurrency, account.getCurrency());
    }

    @Test
    void deposit_validAmount() {
        account.deposit(200.0);

        assertEquals(700.0, account.getBalance());
    }

    @Test
    void deposit_invalidAmount() {
        account.deposit(-50.0);

        assertEquals(500.0, account.getBalance());
    }

    @Test
    void withdraw_validAmount() {
        account.withdraw(200.0);

        assertEquals(300.0, account.getBalance());
    }

    @Test
    void withdraw_invalidAmount() {
        account.withdraw(-50.0);

        assertEquals(500.0, account.getBalance());
    }

    @Test
    void iwithdraw_insufficientBalance() {
        account.withdraw(700.0);

        assertEquals(500.0, account.getBalance());
    }





    @Test
    void iwithdraw_validAmount() {
        account.withdraw(200.0);
        assertEquals(300.0, account.getBalance());
    }

    @Test
    void iwithdraw_invalidAmount() {
        account.withdraw(-50.0);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void inwithdraw_insufficientBalance() {
        account.withdraw(700.0);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void isetCurrency() {
        ICurrencyModel newCurrency = new CurrencyModel("EUR", "Euro");
        account.setCurrency(newCurrency);
        assertEquals(newCurrency, account.getCurrency());
    }
}
