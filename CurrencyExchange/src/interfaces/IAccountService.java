package interfaces;

public interface IAccountService {
    void createAccount(int userId, ICurrencyModel currency);
    void closeAccount(int accountId);
    IAccountModel getAccountById(int accountId);
    void deposit(int accountId, double amount);
    void withdraw(int accountId, double amount);
    void loadData();
    void saveData();
}