package interfaces;

import java.util.List;

public interface IAccountService {
    void createAccount(int userId, ICurrencyModel currency);
    boolean closeAccount(int accountId);
    IAccountModel getAccountById(int accountId);
    List<IAccountModel> findAccountsByUserId(int userId);
    void deposit(int accountId, double amount);
    void withdraw(int accountId, double amount);
    void loadData();
    void saveData();
}