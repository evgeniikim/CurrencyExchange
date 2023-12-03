package repository;

import interfaces.IAccountModel;
import model.AccountModel;
import model.CurrencyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    void saveAccount() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);
        accountRepository.saveAccount(account);

        assertNotNull(accountRepository.findAccountById(1));
    }

    @Test
    void findAccountById_existingAccount() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);
        accountRepository.saveAccount(account);

        IAccountModel foundAccount = accountRepository.findAccountById(1);

        assertEquals(account, foundAccount);
    }

    @Test
    void findAccountById_nonexistentAccount() {
        assertNull(accountRepository.findAccountById(1));
    }

    @Test
    void deleteAccount_existingAccount() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);
        accountRepository.saveAccount(account);

        accountRepository.deleteAccount(1);

        assertNull(accountRepository.findAccountById(1));
    }

    @Test
    void deleteAccount_nonexistentAccount() {
        // Deleting a non-existent account should not throw an exception
        accountRepository.deleteAccount(1);
    }

    @Test
    void updateAccount_existingAccount() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);
        accountRepository.saveAccount(account);


        account.setBalance(200.0);
        accountRepository.updateAccount(account);

        IAccountModel updatedAccount = accountRepository.findAccountById(1);

        assertEquals(200.0, updatedAccount.getBalance());
    }

    @Test
    void updateAccount_nonexistentAccount() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);

        accountRepository.updateAccount(account);
    }

    @Test
    void getNextAccountId() {
        assertEquals(1, accountRepository.getNextAccountId());

        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);
        accountRepository.saveAccount(account);

        assertEquals(2, accountRepository.getNextAccountId());
    }

    @Test
    void saveToFile() {
        IAccountModel account = new AccountModel(1, 1, new CurrencyModel("USD", "US Dollar"), 100.0);
        accountRepository.saveAccount(account);

        assertEquals(0, accountRepository.saveToFile());
    }
}
