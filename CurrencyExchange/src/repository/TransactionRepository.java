package repository;

import Helper.DataHelper;
import exception.ExceptionHandling;
import interfaces.*;

import java.io.IOException;
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

    @Override
    public int saveToFile() {
        try {
            DataHelper.exportData("usertransactions.dat", userTransactions);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            var loadUserTransactions = (Map<Integer, List<ITransactionModel>>) DataHelper.importData("usertransactions.dat");
            userTransactions = loadUserTransactions;
            return 0;
        } catch (IOException | ClassNotFoundException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }
}
