package interfaces;

import java.io.Serializable;
import java.util.Date;

public interface ITransactionModel extends Serializable {
    int getTransactionId();
    int getAccountId();
    TransactionType getType();
    double getAmount();
    Date getTransactionDate();
}