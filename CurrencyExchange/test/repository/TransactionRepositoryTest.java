package repository;

import model.*;
import interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

class TransactionRepositoryTest {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private TransactionModel transaction1;
    private TransactionModel transaction2;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository(accountRepository);

        // Добавляем тестовые аккаунты
        accountRepository.saveAccount(new AccountModel(1, 1, new CurrencyModel("USD", "Dollar"), 100.0));
        accountRepository.saveAccount(new AccountModel(2, 1, new CurrencyModel("EUR", "Euro"), 200.0));

        // Создаем и добавляем тестовые транзакции
        transaction1 = new TransactionModel(1, 1, TransactionType.DEPOSIT, 100.0, new Date());
        transaction2 = new TransactionModel(2, 2, TransactionType.WITHDRAWAL, 50.0, new Date());
        transactionRepository.saveTransaction(transaction1);
        transactionRepository.saveTransaction(transaction2);
    }

    @Test
    void saveTransactionAndFindTransactionsByUserId() {
        List<ITransactionModel> foundTransactions = transactionRepository.findTransactionsByUserId(1);
        assertTrue(foundTransactions.contains(transaction1) && foundTransactions.contains(transaction2),
                "Должны быть найдены все транзакции пользователя");
    }

    @Test
    void findTransactionsByAccountId() {
        List<ITransactionModel> transactions = transactionRepository.findTransactionsByAccountId(1);
        assertTrue(transactions.contains(transaction1), "Должна быть найдена транзакция по ID аккаунта");
    }
}

