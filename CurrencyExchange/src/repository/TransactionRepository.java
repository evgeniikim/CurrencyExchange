package repository;

import interfaces.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository implements ITransactionRepository {
    private Map<Integer, List<ITransactionModel>> userTransactions;

    public TransactionRepository() {
        this.userTransactions = new HashMap<>();
    }

//Метод сохраняет транзакцию в соответствующий список пользователя
    @Override
    public void saveTransaction(ITransactionModel transaction) {
        int userId = transaction.getAccountId();
        userTransactions.computeIfAbsent(userId, k -> new ArrayList<>()).add(transaction);
    }

    //Метод возвращает список транзакций для указанного пользователя.
    @Override
    public List<ITransactionModel> findTransactionsByUserId(int userId) {
        return userTransactions.getOrDefault(userId, new ArrayList<>());
    }
    @Override
    public int getNextTransactionId() {
        return userTransactions.keySet()
                .stream()
                .max(Integer::compare)
                .map(maxId-> maxId +1)
                .orElse(1);
    }
}
