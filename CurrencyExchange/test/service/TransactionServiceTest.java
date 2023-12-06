package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import repository.*;
import interfaces.*;
import model.*;

class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository, accountRepository);
    }

    @Test
    void performTransaction_DepositTest() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "Dollar"), 100.0);
        when(accountRepository.findAccountById(1)).thenReturn(account);

        transactionService.performTransaction(1, TransactionType.DEPOSIT, 50.0);

        verify(accountRepository, times(1)).findAccountById(1);
        verify(transactionRepository, times(1)).saveTransaction(any(ITransactionModel.class));
        verify(accountRepository, times(1)).updateAccount(account);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void transferFundsTest() {
        IAccountModel accountFrom = new AccountModel(1, 1, new CurrencyModel("USD", "Dollar"), 100.0);
        IAccountModel accountTo = new AccountModel(2, 2, new CurrencyModel("EUR", "Euro"), 50.0);

        when(accountRepository.findAccountById(1)).thenReturn(accountFrom);
        when(accountRepository.findAccountById(2)).thenReturn(accountTo);

        boolean result = transactionService.transferFunds(1, 2, 50.0);

        assertTrue(result);
        assertEquals(50.0, accountFrom.getBalance());
        assertEquals(100.0, accountTo.getBalance());
        verify(transactionRepository, times(2)).saveTransaction(any(ITransactionModel.class));
        verify(accountRepository, times(2)).updateAccount(any(IAccountModel.class));
    }

    @Test
    void getTransactionHistoryByUserIdTest() {
        List<ITransactionModel> expectedTransactions = List.of(
                new TransactionModel(1, 1, TransactionType.DEPOSIT, 50.0, new Date()),
                new TransactionModel(2, 1, TransactionType.WITHDRAWAL, 20.0, new Date())
        );

        when(transactionRepository.findTransactionsByUserId(1)).thenReturn(expectedTransactions);

        List<ITransactionModel> actualTransactions = transactionService.getTransactionHistoryByUserId(1);

        assertEquals(expectedTransactions, actualTransactions);
    }
    @Test
    void getTransactionHistoryByAccountIdTest() {
        List<ITransactionModel> expectedTransactions = List.of(
                new TransactionModel(1, 1, TransactionType.DEPOSIT, 50.0, new Date())
        );

        when(transactionRepository.findTransactionsByAccountId(1)).thenReturn(expectedTransactions);

        List<ITransactionModel> actualTransactions = transactionService.getTransactionHistoryByAccountId(1);

        assertEquals(expectedTransactions, actualTransactions);
    }
    @Test
    void exchangeCurrencyTest() {
        IAccountModel accountFrom = new AccountModel(1, 1, new CurrencyModel("USD", "Dollar"), 100.0);
        IAccountModel accountTo = new AccountModel(2, 2, new CurrencyModel("EUR", "Euro"), 50.0);

        when(accountRepository.findAccountById(1)).thenReturn(accountFrom);
        when(accountRepository.findAccountById(2)).thenReturn(accountTo);

        transactionService.exchangeCurrency(1, 2, 50.0, 1.2);

        assertEquals(50.0, accountFrom.getBalance());
        assertEquals(110.0, accountTo.getBalance());
        verify(transactionRepository, times(2)).saveTransaction(any(ITransactionModel.class));
        verify(accountRepository, times(2)).updateAccount(any(IAccountModel.class));
    }


}
