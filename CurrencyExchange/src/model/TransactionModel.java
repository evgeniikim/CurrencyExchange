package model;

import interfaces.*;

import java.io.Serializable;
import java.util.Date;


public class TransactionModel implements ITransactionModel, Serializable {
    private static final long serialVersionUID = 1L;
    private int transactionId;
    private int accountId;
    private TransactionType type;
    private double amount;
    private Date transactionDate;

    public TransactionModel(int transactionId, int accountId, TransactionType type, double amount, Date transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    @Override
    public int getTransactionId() {
        return transactionId;
    }

    @Override
    public int getAccountId() {
        return accountId;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", type=" + type +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}