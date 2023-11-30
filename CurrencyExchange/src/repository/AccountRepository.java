package repository;

import interfaces.IAccountModel;
import interfaces.IAccountRepository;
import Helper.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import exception.*;

public class AccountRepository implements IAccountRepository {
    private Map<Integer, IAccountModel> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    @Override
    public void saveAccount(IAccountModel account) {
        accounts.put(account.getAccountId(), account);
        System.out.println("Счет успешно сохранен.");
    }

    @Override
    public IAccountModel findAccountById(int accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void deleteAccount(int accountId) {
        accounts.remove(accountId);
        System.out.println("Счет успешно удален.");
    }

    @Override
    public void updateAccount(IAccountModel account) {
        if (accounts.containsKey(account.getAccountId())) {
            accounts.put(account.getAccountId(), account);
            System.out.println("Счет успешно обновлен.");
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");
        }
    }

    @Override
    public int getNextAccountId() {
        return accounts.keySet()
                       .stream()
                       .max(Integer::compare)
                       .map(maxId-> maxId +1)
                       .orElse(1);
    }


    @Override
    public int saveToFile() {
        try {
            DataHelper.exportData("accounts.dat", accounts);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            var loadAccounts = (Map<Integer, IAccountModel>) DataHelper.importData("accounts.dat");
            accounts = loadAccounts;
            return 0;
        } catch (IOException | ClassNotFoundException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

}