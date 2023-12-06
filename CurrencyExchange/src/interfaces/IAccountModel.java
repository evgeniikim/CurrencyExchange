package interfaces;

import java.io.Serializable;

public interface IAccountModel extends Serializable{
    int getAccountId();
    int getUserId();
    ICurrencyModel getCurrency();
    double getBalance();
    void setCurrency(ICurrencyModel currency);
    String getCurrencyCode();
    void deposit(double amount);
    void withdraw(double amount);
    void setBalance(double balance);
}