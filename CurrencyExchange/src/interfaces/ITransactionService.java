package interfaces;

import java.util.List;

public interface ITransactionService {
    void performTransaction(ITransactionModel transaction);
    List<ITransactionModel> getTransactionHistory(int userId);
    void exchangeCurrency(int accountIdFrom, int accountIdTo, double amount);
    List<ITransactionModel> getTransactionHistoryByCurrencyCode(String currencyCode);
}