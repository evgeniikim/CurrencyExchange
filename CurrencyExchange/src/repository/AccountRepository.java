package repository;

import com.google.gson.reflect.TypeToken;
import interfaces.IAccountModel;
import interfaces.IAccountRepository;
import Helper.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exception.*;
import model.AccountModel;

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
    public List<IAccountModel> findAccountsByUserId(int userId) {
        List<IAccountModel> userAccounts = new ArrayList<>();
        for (IAccountModel account : accounts.values()) {
            if (account.getUserId() == userId) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
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
            DataHelper.exportDataToJson("accounts.json", accounts);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            Type type = new TypeToken<Map<Integer, AccountModel>>(){}.getType();
            Map<Integer, IAccountModel> loadAccounts = DataHelper.importDataFromJson("accounts.json", type);
            if(loadAccounts!=null) {
                accounts = loadAccounts;
            }
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

}