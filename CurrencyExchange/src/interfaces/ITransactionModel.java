package interfaces;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public interface ITransactionModel extends Serializable {
    int getTransactionId();
    int getAccountId();
    TransactionType getType();
    double getAmount();
    Date getTransactionDate();
    String getCurrencyCode();
    void setCurrency(ICurrencyModel currency);
}