package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import interfaces.*;
import model.*;

import java.util.List;

class AccountServiceTest {
    @Mock
    private IAccountRepository accountRepository;

    private AccountService accountService;
    private ICurrencyModel currencyModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository);

        // Create мок валюты
        currencyModel = new CurrencyModel("USD", "Dollar");
    }

    @Test
    void createAccountTest() {
        accountService.createAccount(1, currencyModel);

        verify(accountRepository, times(1)).saveAccount(any(IAccountModel.class));
    }

    @Test
    void closeAccountTest() {
        when(accountRepository.findAccountById(anyInt())).thenReturn(new AccountModel(1, 1, currencyModel, 100.0));
        boolean result = accountService.closeAccount(1);

        assertTrue(result);
        verify(accountRepository, times(1)).deleteAccount(1);
    }

    @Test
    void getAccountByIdTest() {
        IAccountModel expectedAccount = new AccountModel(1, 1, currencyModel, 100.0);
        when(accountRepository.findAccountById(1)).thenReturn(expectedAccount);

        IAccountModel actualAccount = accountService.getAccountById(1);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void findAccountsByUserIdTest() {
        List<IAccountModel> expectedAccounts = List.of(
                new AccountModel(1, 1, currencyModel, 100.0),
                new AccountModel(2, 1, currencyModel, 200.0)
        );

        when(accountRepository.findAccountsByUserId(1)).thenReturn(expectedAccounts);

        List<IAccountModel> actualAccounts = accountService.findAccountsByUserId(1);

        assertEquals(expectedAccounts, actualAccounts);
        verify(accountRepository, times(1)).findAccountsByUserId(1);
    }

    @Test
    void depositTest() {
        IAccountModel account = new AccountModel(1, 1, currencyModel, 100.0);
        when(accountRepository.findAccountById(1)).thenReturn(account);

        accountService.deposit(1, 50.0);

        verify(accountRepository, times(1)).findAccountById(1);
        verify(accountRepository, times(1)).updateAccount(account);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void withdrawTest_Successful() {
        IAccountModel account = new AccountModel(1, 1, currencyModel, 100.0);
        when(accountRepository.findAccountById(1)).thenReturn(account);

        accountService.withdraw(1, 50.0);

        verify(accountRepository, times(1)).findAccountById(1);
        verify(accountRepository, times(1)).updateAccount(account);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void withdrawTest_FailureInsufficientFunds() {
        IAccountModel account = new AccountModel(1, 1, currencyModel, 50.0);
        when(accountRepository.findAccountById(1)).thenReturn(account);

        accountService.withdraw(1, 100.0);

        verify(accountRepository, times(1)).findAccountById(1);
        verify(accountRepository, never()).updateAccount(account);
        assertEquals(50.0, account.getBalance(), "Баланс не должен измениться при недостаточных средствах");
    }


}
