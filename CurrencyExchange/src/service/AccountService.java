package service;

import interfaces.*;
import model.AccountModel;

import java.util.List;

public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //Метод открытия счета
    @Override
    public void createAccount(int userId, ICurrencyModel currency) {
        int newAccountId = generateAccountId();

        IAccountModel newAccount = new AccountModel(newAccountId, userId, currency, 0.0);

        accountRepository.saveAccount(newAccount);

        System.out.println("Счет успешно создан. Идентификатор счета: " + newAccountId);
    }

    @Override
    public boolean closeAccount(int accountId) {
        // Поиск счета по идентификатору
        IAccountModel account = accountRepository.findAccountById(accountId);

        if (account != null) {
            // Удаление счета из репозитория
            accountRepository.deleteAccount(accountId);
            System.out.println("Счет успешно закрыт.");
            return true;
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");
            return false;
        }
    }

    @Override
    public IAccountModel getAccountById(int accountId) {
        // Поиск счета по идентификатору
        return accountRepository.findAccountById(accountId);
    }

    @Override
    public List<IAccountModel> findAccountsByUserId(int userId)
    {
        return accountRepository.findAccountsByUserId(userId);
    }

    @Override
    public void deposit(int accountId, double amount) {
        // Поиск счета по идентификатору
        IAccountModel account = accountRepository.findAccountById(accountId);

        if (account != null) {
            // Внесение депозита на счет
            account.deposit(amount);
            // Обновление счета в репозитории
            accountRepository.updateAccount(account);
            System.out.println("Депозит успешно внесен.");
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");
        }
    }

    @Override
    public void withdraw(int accountId, double amount) {
        // Поиск счета по идентификатору
        IAccountModel account = accountRepository.findAccountById(accountId);

        if (account != null) {
            // Снятие средств со счета
            if (account.getBalance() >= amount) {
                account.withdraw(amount);
                // Обновление счета в репозитории
                accountRepository.updateAccount(account);
                System.out.println("Средства успешно сняты.");
            } else {
                System.out.println("Недостаточно средств на счете.");
            }
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");
        }
    }

    // Метод генерации идентификатора счета
    private int generateAccountId() {
        return accountRepository.getNextAccountId();
    }

    @Override
    public void loadData() {
        accountRepository.loadFromFile();
    }
    @Override
    public void saveData() {
        accountRepository.saveToFile();
    }
}
