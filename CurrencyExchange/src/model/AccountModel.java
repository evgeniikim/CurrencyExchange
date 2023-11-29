package model;

import interfaces.IAccountModel;
import interfaces.ICurrencyModel;

public class AccountModel implements IAccountModel {
    private int accountId;
    private int userId;
    private double balance;
    private ICurrencyModel currency;

    public AccountModel(int accountId, int userId, double balance, ICurrencyModel currency) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

    public int getAccountId(){
        return accountId;

    }
    public int getUserId(){
        return userId;
    }
    public ICurrencyModel getCurrency(){
        return currency;
    }
    public double getBalance(){
        return balance;
    }
    public void setCurrency(ICurrencyModel currency){
        this.currency = currency;
    }
    public void deposit(double amount){
        if (amount > 0) {
            balance += amount;
            System.out.println("Внесение средств успешно. Новый баланс: " + balance);
        } else {
            System.out.println("Недопустимая сумма для внесения. Сумма должна быть больше 0.");
        }

    }
    public void withdraw(double amount){
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Снятие средств успешно. Новый баланс: " + balance);
        } else {
            System.out.println("Недопустимая сумма для снятия или недостаточно средств на счете.");
        }
    }
}