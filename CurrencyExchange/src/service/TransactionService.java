package service;

import interfaces.*;
import repository.TransactionRepository;
import model.TransactionModel;

import java.util.List;

public class TransactionService implements ITransactionService {
    private ITransactionRepository transactionRepository;
    private IAccountRepository accountRepository;
    private ICurrencyService currencyService;


    // Конструктор по умолчанию без параметров
    public TransactionService() {
        this.transactionRepository = new TransactionRepository();
    }

    public TransactionService(ITransactionRepository transactionRepository, IAccountRepository accountRepository, ICurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.currencyService = currencyService;
    }

    @Override
    public void performTransaction(ITransactionModel transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    @Override
    public List<ITransactionModel> getTransactionHistory(int userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }

    //Метод обмена валюты
    @Override
    public void exchangeCurrency(int accountIdFrom, int accountIdTo, double amount) {
        // Получение информации о валютах счетов
        IAccountModel accountFrom = accountRepository.findAccountById(accountIdFrom);
        IAccountModel accountTo = accountRepository.findAccountById(accountIdTo);

        // Получение текущих курсов валют
        double exchangeRateFromTo = currencyService.getCurrencyRate(accountFrom.getCurrency().getCode() + "_" + accountTo.getCurrency().getCode()).getRate();
        double exchangeRateToFrom = currencyService.getCurrencyRate(accountTo.getCurrency().getCode() + "_" + accountFrom.getCurrency().getCode()).getRate();

        // Конвертация суммы из одной валюты в другую
        double amountInFromCurrency = amount * exchangeRateFromTo;
        double amountInToCurrency = amountInFromCurrency / exchangeRateToFrom;

        // Списание средств со счета и зачисление на другой
        accountFrom.withdraw(amount);
        accountTo.deposit(amountInToCurrency);

        // Обновление данных счетов в репозитории
        accountRepository.updateAccount(accountFrom);
        accountRepository.updateAccount(accountTo);

        // Создание записи о транзакции
        TransactionType transactionType = TransactionType.CURRENCY_EXCHANGE;
        ITransactionModel transaction = new TransactionModel(accountFrom.getUserId(), transactionType, amount);
        transactionRepository.saveTransaction(transaction);
        System.out.println("Обмен валюты выполнен успешно.");
    }

    // Метод генерации идентификатора транзакции
    private int getNextTransactionId() {
        return transactionRepository.getNextTransactionId();
    }
}