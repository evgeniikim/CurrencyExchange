package service;

import interfaces.*;
import java.util.List;

public class TransactionService implements ITransactionService {
    private ITransactionRepository transactionRepository;

    public TransactionService(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void performTransaction(ITransactionModel transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    @Override
    public List<ITransactionModel> getTransactionHistory(int userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }

    @Override
    public void exchangeCurrency(int accountIdFrom, int accountIdTo, double amount) {
        // Логика обмена валюты
        // Можно добавить проверки и другую логику обмена
    }
}
