package interfaces;

import java.util.List;

public interface ITransactionService {
    void performTransaction(int accountId, TransactionType type, double amount);
    List<ITransactionModel> getTransactionHistoryByUserId(int userId);
    List<ITransactionModel> getTransactionHistoryByAccountId(int accountId);
    boolean transferFunds(int accountIdFrom, int accountIdTo, double amount);
    void exchangeCurrency(int accountIdFrom, int accountIdTo, double amount, double exchangeRate);
    List<ITransactionModel> getTransactionHistoryByCurrencyCode(String currencyCode);
    void loadData();
    void saveData();
}