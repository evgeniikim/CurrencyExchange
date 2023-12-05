package repository;

import Helper.DataHelper;
import com.google.gson.reflect.TypeToken;
import exception.ExceptionHandling;
import interfaces.*;
import model.TransactionModel;

import java.io.IOException;
import java.lang.reflect.Type;
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
            DataHelper.exportDataToJson("usertransactions.json", userTransactions);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            Type type = new TypeToken<Map<Integer, List<TransactionModel>>>(){}.getType();
            Map<Integer, List<ITransactionModel>> loadUserTransactions = DataHelper.importDataFromJson("usertransactions.json", type);
            if(loadUserTransactions!=null) {
                userTransactions = loadUserTransactions;
            }
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    //Метод вывода всех транзакций
    @Override
    public List<ITransactionModel> getAllTransactions() {
        List<ITransactionModel> allTransactions = new ArrayList<>();
        userTransactions.values().forEach(allTransactions::addAll);
        return allTransactions;
    }
}
