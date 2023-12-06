package model;

import interfaces.IAccountModel;
import interfaces.ICurrencyModel;
import java.io.Serializable;

public class AccountModel implements IAccountModel, Serializable {
    private static final long serialVersionUID = 1L;
    private int accountId;          // Уникальный идентификатор счета
    private int userId;             // Идентификатор пользователя, которому принадлежит счет
    private CurrencyModel currency;// Модель валюты счета
    private double balance;         // Баланс счета

    public AccountModel(int accountId, int userId, ICurrencyModel currency, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.currency = (CurrencyModel) currency;
        this.balance = balance;
    }

    @Override
    public int getAccountId() {
        return accountId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public ICurrencyModel getCurrency() {
        return currency;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setCurrency(ICurrencyModel currency) {
        this.currency = (CurrencyModel)currency;
    }
    @Override
    public String getCurrencyCode() {
        return currency.getCode();
    }


    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Внесение средств успешно. Новый баланс: " + balance);
        } else {
            System.out.println("Недопустимая сумма для внесения. Сумма должна быть больше 0.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Снятие средств успешно. Новый баланс: " + balance);
        } else {
            System.out.println("Недопустимая сумма для снятия или недостаточно средств на счете.");
        }
    }
    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
}