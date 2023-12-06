package interfaces;

import model.TransactionModel;

import java.util.List;

public interface ITransactionRepository extends IExportImportFile  {
    void saveTransaction(ITransactionModel transaction);
    List<ITransactionModel> findTransactionsByUserId(int userId);
    List<ITransactionModel> findTransactionsByAccountId(int accountId);
    List<ITransactionModel> findTransactionsByCurrencyCode(String currencyCode);
    int getNextTransactionId();
    //List<ITransactionModel> getAllTransactions();
}