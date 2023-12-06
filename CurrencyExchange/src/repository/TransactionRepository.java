package repository;

import Helper.DataHelper;
import com.google.gson.reflect.TypeToken;
import exception.ExceptionHandling;
import interfaces.*;
import model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepository implements ITransactionRepository {
    private List<ITransactionModel> transactions = new ArrayList<>();
    private final AccountRepository accountRepository;

    public TransactionRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveTransaction(ITransactionModel transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<ITransactionModel> findTransactionsByUserId(int userId) {
        List<Integer> accountIds = accountRepository.findAccountsByUserId(userId)
                .stream()
                .map(IAccountModel::getAccountId)
                .collect(Collectors.toList());

        return transactions.stream()
                .filter(t -> accountIds.contains(t.getAccountId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ITransactionModel> findTransactionsByAccountId(int accountId) {
        return transactions.stream()
                .filter(t -> t.getAccountId() == accountId)
                .collect(Collectors.toList());
    }
    @Override
    public List<ITransactionModel> findTransactionsByCurrencyCode(String currencyCode) {
        return transactions.stream()
                .filter(t -> t.getCurrencyCode() == currencyCode)
                .collect(Collectors.toList());
    }
    @Override
    public int getNextTransactionId() {
        return transactions.stream()
                .mapToInt(ITransactionModel::getTransactionId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public int saveToFile() {
        try {
            DataHelper.exportDataToJson("usertransactions.json", transactions);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            Type type = new TypeToken<List<ITransactionModel>>(){}.getType();
            List<ITransactionModel> loadUserTransactions = DataHelper.importDataFromJson("usertransactions.json", type);
            if(loadUserTransactions!=null) {
                transactions = loadUserTransactions;
            }
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }


}
