package interfaces;

import java.util.List;

public interface ITransactionRepository extends IExportImportFile  {
    void saveTransaction(ITransactionModel transaction);
    List<ITransactionModel> findTransactionsByUserId(int userId);

    int getNextTransactionId();
}