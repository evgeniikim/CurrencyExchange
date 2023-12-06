package service;

import interfaces.*;
import repository.*;
import model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService  implements ITransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void performTransaction(int accountId, TransactionType type, double amount) {
        IAccountModel account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Аккаунт не найден");
        }

        switch (type) {
            case DEPOSIT:
                account.deposit(amount);
                break;
            case WITHDRAWAL:
                if (account.getBalance() < amount) {
                    throw new IllegalArgumentException("Недостаточно средств на счете");
                }
                account.withdraw(amount);
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип транзакции");
        }

        TransactionModel transaction = new TransactionModel(
                transactionRepository.getNextTransactionId(),
                accountId,
                type,
                amount,
                convertToDate(LocalDateTime.now())
        );

        transactionRepository.saveTransaction(transaction);
        accountRepository.updateAccount(account);
    }

    @Override
    public boolean transferFunds(int accountIdFrom, int accountIdTo, double amount) {
        IAccountModel accountFrom = accountRepository.findAccountById(accountIdFrom);
        if (accountFrom == null) {
            System.out.println("Аккаунт отправителя не найден");
            return false;
        }

        IAccountModel accountTo = accountRepository.findAccountById(accountIdTo);
        if (accountTo == null) {
            System.out.println("Аккаунт получателя не найден");
            return false;
        }

        if (accountFrom.getBalance() < amount) {
            System.out.println("Недостаточно средств на счете отправителя для перевода");
            return false;
        }

        accountFrom.withdraw(amount);
        accountTo.deposit(amount);

        // Создание транзакций для отправителя и получателя
        TransactionModel transactionFrom = new TransactionModel(
                transactionRepository.getNextTransactionId(),
                accountIdFrom,
                TransactionType.WITHDRAWAL,
                amount,
                convertToDate(LocalDateTime.now())
        );
        transactionRepository.saveTransaction(transactionFrom);

        TransactionModel transactionTo = new TransactionModel(
                transactionRepository.getNextTransactionId(),
                accountIdTo,
                TransactionType.DEPOSIT,
                amount,
                convertToDate(LocalDateTime.now())
        );
        transactionRepository.saveTransaction(transactionTo);

        // Обновление информации об обоих счетах
        accountRepository.updateAccount(accountFrom);
        accountRepository.updateAccount(accountTo);
        return true;
    }


    @Override
    public List<ITransactionModel> getTransactionHistoryByUserId(int userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }

    @Override
    public List<ITransactionModel> getTransactionHistoryByAccountId(int accountId) {
        return transactionRepository.findTransactionsByAccountId(accountId);
    }

    @Override
    public void exchangeCurrency(int accountIdFrom, int accountIdTo, double amount, double exchangeRate) {
        IAccountModel accountFrom = accountRepository.findAccountById(accountIdFrom);
        IAccountModel accountTo = accountRepository.findAccountById(accountIdTo);

        if (accountFrom == null || accountTo == null) {
            throw new IllegalArgumentException("Один или оба аккаунта не найдены");
        }

        if (accountFrom.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счете отправителя");
        }

        accountFrom.withdraw(amount);
        accountTo.deposit(amount * exchangeRate);

        TransactionModel transactionFrom = new TransactionModel(
                transactionRepository.getNextTransactionId(),
                accountIdFrom,
                TransactionType.WITHDRAWAL,
                amount,
                convertToDate(LocalDateTime.now())
        );
        TransactionModel transactionTo = new TransactionModel(
                transactionRepository.getNextTransactionId(),
                accountIdTo,
                TransactionType.DEPOSIT,
                amount * exchangeRate,
                convertToDate(LocalDateTime.now())
        );

        transactionRepository.saveTransaction(transactionFrom);
        transactionRepository.saveTransaction(transactionTo);
        accountRepository.updateAccount(accountFrom);
        accountRepository.updateAccount(accountTo);
    }

    //Метод обмена валюты
/*
    public void exchangeCurrency2(int accountIdFrom, int accountIdTo, double amount) {
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
        var transactionId = transactionRepository.getNextTransactionId();
        var transactionDate = new Date();
        ITransactionModel transaction = new TransactionModel(transactionId, accountFrom.getUserId(), transactionType, amount, transactionDate);
        transactionRepository.saveTransaction(transaction);
        System.out.println("Обмен валюты выполнен успешно.");
    }
*/
    // Метод генерации идентификатора транзакции
    private int getNextTransactionId() {
        return transactionRepository.getNextTransactionId();
    }

    //Метод, который будет возвращать список транзакций всех пользователей по указанному коду валюты
    @Override
    public List<ITransactionModel> getTransactionHistoryByCurrencyCode(String currencyCode) {
        List<ITransactionModel> allTransactions = transactionRepository.findTransactionsByCurrencyCode(currencyCode);
        return allTransactions;
    }

    @Override
    public void loadData() {
        transactionRepository.loadFromFile();
    }
    @Override
    public void saveData() {
        transactionRepository.saveToFile();
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}