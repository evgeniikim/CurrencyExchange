package repository;

import interfaces.ITransactionModel;
import interfaces.TransactionType;
import model.TransactionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private static final String USER_TRANSACTIONS_FILE = "usertransactions_test.dat";

    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TransactionRepository();
    }

    @Test
    void saveTransaction() {
        ITransactionModel transaction = new TransactionModel(1, 2, TransactionType.WITHDRAWAL, 200, new Date(2023, 12, 3));

        repository.saveTransaction(transaction);

        List<ITransactionModel> transactions = repository.findTransactionsByUserId(1);

    }

    @Test
    void findTransactionsByUserId() {
        ITransactionModel transaction1 = new TransactionModel(1, 1, TransactionType.DEPOSIT, 4000, new Date(2023, 12, 2));
        ITransactionModel transaction2 = new TransactionModel(1, 2, TransactionType.DEPOSIT, 6000, new Date(2023, 12, 2));

        repository.saveTransaction(transaction1);
        repository.saveTransaction(transaction2);

        List<ITransactionModel> transactions = repository.findTransactionsByUserId(1);

        assertTrue(transactions.contains(transaction1));

    }

    @Test
    void getNextTransactionId() {
        ITransactionModel transaction1 = new TransactionModel(1, 1, TransactionType.DEPOSIT, 3000, new Date(2023, 12, 3));
        ITransactionModel transaction2 = new TransactionModel(2, 2, TransactionType.DEPOSIT, 4000, new Date(2023, 12, 3));

        repository.saveTransaction(transaction1);
        repository.saveTransaction(transaction2);
        assertEquals(3, repository.getNextTransactionId());
    }

    @Test
    void saveToFile() {
        ITransactionModel transaction1 = new TransactionModel(1, 1, TransactionType.DEPOSIT, 2500, new Date(2023, 12, 3));
        ITransactionModel transaction2 = new TransactionModel(2, 2, TransactionType.DEPOSIT, 3200, new Date(2023, 12, 3));

        repository.saveTransaction(transaction1);
        repository.saveTransaction(transaction2);

        int result = repository.saveToFile();
        assertEquals(0, result);
    }

    @Test
    void loadFromFile() {
        ITransactionModel transaction1 = new TransactionModel(1, 1, TransactionType.DEPOSIT, 5000, new Date(2023, 12, 3));
        ITransactionModel transaction2 = new TransactionModel(2, 2, TransactionType.DEPOSIT, 5200, new Date(2023, 12, 3));

        repository.saveTransaction(transaction1);
        repository.saveTransaction(transaction2);
        repository.saveToFile();

        repository = new TransactionRepository();

        int result = repository.loadFromFile();

        assertEquals(0, result);

        List<ITransactionModel> transactions = repository.findTransactionsByUserId(1);

    }

    @Test
    void getAllTransactions() {
        ITransactionModel transaction1 = new TransactionModel(1, 1, TransactionType.DEPOSIT, 1500, new Date(2023, 12, 3));
        ITransactionModel transaction2 = new TransactionModel(2, 2, TransactionType.DEPOSIT, 2200, new Date(2023, 12, 3));

        repository.saveTransaction(transaction1);
        repository.saveTransaction(transaction2);

        List<ITransactionModel> allTransactions = repository.getAllTransactions();

        assertTrue(allTransactions.contains(transaction1));
        assertTrue(allTransactions.contains(transaction2));
    }
}
