package interfaces;

import java.util.Date;

public interface ITransactionModel {
    int getTransactionId();
    int getAccountId();
    TransactionType getType();
    double getAmount();
    Date getTransactionDate();
}