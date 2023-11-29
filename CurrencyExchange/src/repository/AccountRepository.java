package repository;

import interfaces.IAccountModel;
import interfaces.IAccountRepository;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository implements IAccountRepository {
    private final Map<Integer, IAccountModel> accounts;

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
}