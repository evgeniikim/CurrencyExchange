package repository;

import interfaces.IAccountModel;
import interfaces.IAccountRepository;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository implements IAccountRepository {
    private Map<Integer, IAccountModel> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public void saveAccount(IAccountModel account){
        accounts.put(account.getAccountId(), account);
        System.out.println("Счет успешно сохранен.");
    }
    public IAccountModel findAccountById(int accountId){
        return accounts.get(accountId);
    }
    public void deleteAccount(int accountId){
        accounts.remove(accountId);
        System.out.println("Счет успешно удален.");
    }

    public void updateAccount(IAccountModel account){
        if (accounts.containsKey(account.getAccountId())) {
            accounts.put(account.getAccountId(), account);
            System.out.println("Счет успешно обновлен.");
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");
        }
    }

    public Map<Integer, IAccountModel> getAccounts() {
        return accounts;
    }
}