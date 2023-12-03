package model;

import interfaces.ICurrencyModel;
import interfaces.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;


class TransactionModelTest {

    private TransactionModel transaction;

    @BeforeEach
    void setUp() {
        // Предполагается, что у вас есть объекты для передачи в конструктор
        Date currentDate = new Date();
        ICurrencyModel currency = new CurrencyModel("USD", "US Dollar");
        transaction = new TransactionModel(1, 1001, TransactionType.DEPOSIT, 500.0, new Date (2023, 12, 3));
        transaction.setCurrency(currency);
    }

    @Test
    void getTransactionId() {
        assertEquals(1, transaction.getTransactionId());
    }

    @Test
    void getAccountId() {
        assertEquals(1001, transaction.getAccountId());
    }

    @Test
    void getType() {
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }

    @Test
    void getAmount() {
        assertEquals(500.0, transaction.getAmount());
    }

    @Test
    void getTransactionDate() {
        assertNotNull(transaction.getTransactionDate());
    }

    @Test
    void testToString() {
        String expectedString = "TransactionModel{transactionId=1, accountId=1001, type=DEPOSIT, amount=500.0, transactionDate=" + transaction.getTransactionDate() + '}';
        assertEquals(expectedString, transaction.toString());
    }

    @Test
    void getCurrencyCode() {
        assertEquals("USD", transaction.getCurrencyCode());
    }
}
