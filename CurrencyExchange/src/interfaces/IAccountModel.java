package interfaces;

public interface IAccountModel {
    int getAccountId();
    int getUserId();
    ICurrencyModel getCurrency();
    double getBalance();
    void setCurrency(ICurrencyModel currency);
    void deposit(double amount);
    void withdraw(double amount);
}