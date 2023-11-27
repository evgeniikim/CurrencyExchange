package interfaces;

import java.util.List;

public interface ITransactionRepository {
    void saveTransaction(ITransactionModel transaction);
    List<ITransactionModel> findTransactionsByUserId(int userId);
}