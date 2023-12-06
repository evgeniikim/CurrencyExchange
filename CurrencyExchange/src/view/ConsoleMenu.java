package view;

import exception.ExceptionHandling;
import interfaces.*;
import model.CurrencyModel;
import model.CurrencyRateModel;
import model.UserModel;
import service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ConsoleMenu  {
    private final Scanner scanner;

    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final CurrencyService currencyService;
    private IUserModel currentUser = null;

    public ConsoleMenu(AccountService accountService, UserService userService, TransactionService transactionService, CurrencyService currencyService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.currentUser = null;
        this.scanner = new Scanner(System.in);
        userService.loadData();
        if(userService.getUsersCount()==0)
        {
            userService.generateStaticUsers();
            userService.saveData();
        }

        transactionService.loadData();
        currencyService.loadData();
        accountService.loadData();
    }

    //TODO deprecated
    protected void finalize() throws Throwable {
        try {
            userService.saveData();
            transactionService.saveData();
            currencyService.saveData();
            accountService.saveData();
        } finally {
            super.finalize();
        }
    }

    private void saveData() {
        try {
            userService.saveData();
            transactionService.saveData();
            currencyService.saveData();
            accountService.saveData();
        } catch (Exception e) {
            ExceptionHandling.handleException(e);
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n*** Обменный пункт валюты ***");
            if (currentUser == null) {
                System.out.println("1. Регистрация");
                System.out.println("2. Вход в систему");
            } else {
                System.out.println("3. Управление аккаунтами");
                System.out.println("4. Выполнить транзакцию");
                System.out.println("5. Просмотр истории операций");
                if (currentUser.getRole()  == UserRole.ADMIN) {
                    System.out.println("6. Управление валютами");
                    System.out.println("7. Установка курсов валют");
                }
                System.out.println("8. Выход из учетной записи");
            }
            System.out.println("0. Выход из приложения");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    manageAccounts();
                    break;
                case 4:
                    performTransaction();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    if (currentUser.getRole() == UserRole.ADMIN) {
                        manageCurrencies();
                    }
                    break;
                case 7:
                    if (currentUser.getRole() == UserRole.ADMIN) {
                        setCurrencyRates();
                    }
                    break;
                case 8:
                    saveData();
                    logoutUser();
                    break;
                case 0:
                    running = false;
                    saveData();
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void loginUser() {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        IUserModel user = userService.login(email, password);

        if (user != null) {
            System.out.println("Успешный вход в систему.");
            currentUser = user;
        } else {
            System.out.println("Неверный email или пароль.");
        }
    }

    private void registerUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        var result = userService.registerUser(name, email, password);
        if(result){
            System.out.println("Пользователь успешно зарегистрирован.");
        } else {
            System.out.println("Ошибка ввода данных.");
        }
    }

    //---------------------------------------------------------------------------------------

    private void manageAccounts() {
        System.out.println("Управление аккаунтами:");
        System.out.println("1. Создать новый аккаунт");
        System.out.println("2. Просмотреть мои аккаунты");
        System.out.println("3. Обновить аккаунт");
        System.out.println("4. Удалить аккаунт");
        System.out.println("0. Назад");
        System.out.print("Выберите опцию: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                viewAccounts();
                break;
            case 3:
                System.out.println("Метод пока не реализован!!!");
                //updateAccount();
                break;
            case 4:
                deleteAccount();
                break;
            case 0:
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
        }
    }

    private void createAccount() {
        System.out.println("Выберите валюту для нового счета:");
        List<ICurrencyModel> currencies = currencyService.getAllCurrencies();
        for (int i = 0; i < currencies.size(); i++) {
            System.out.println((i + 1) + ". " + currencies.get(i).getName());
        }

        System.out.print("Введите номер валюты: ");
        int currencyChoice = scanner.nextInt();

        if (currencyChoice < 1 || currencyChoice > currencies.size()) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return;
        }

        ICurrencyModel selectedCurrency = currencies.get(currencyChoice - 1);
        accountService.createAccount(currentUser.getUserId(), selectedCurrency);

        System.out.println("Счет успешно создан в валюте " + selectedCurrency.getName());
    }

    private void viewAccounts() {
        List<IAccountModel> accounts = accountService.findAccountsByUserId(currentUser.getUserId());
        if (accounts.isEmpty()) {
            System.out.println("У вас нет аккаунтов.");
        } else {
            for (IAccountModel account : accounts) {
                System.out.println("Аккаунт ID: " + account.getAccountId() + ", Валюта: " + account.getCurrency().getName() + ", Баланс: " + account.getBalance());
            }
        }
    }

    private void deleteAccount() {
        List<IAccountModel> userAccounts = accountService.findAccountsByUserId(currentUser.getUserId());
        if (userAccounts.isEmpty()) {
            System.out.println("У вас нет аккаунтов для удаления.");
            return;
        }

        System.out.println("Выберите аккаунт для удаления:");
        for (int i = 0; i < userAccounts.size(); i++) {
            IAccountModel account = userAccounts.get(i);
            System.out.println((i + 1) + ". Аккаунт ID: " + account.getAccountId() + ", Баланс: " + account.getBalance());
        }

        System.out.print("Введите номер аккаунта: ");
        int accountChoice = scanner.nextInt() - 1;
        if (accountChoice < 0 || accountChoice >= userAccounts.size()) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return;
        }

        IAccountModel selectedAccount = userAccounts.get(accountChoice);
        if (selectedAccount.getBalance() > 0) {
            System.out.println("На вашем счете есть средства. Сначала переведите их на другой ваш счет.");
            //TODO Здесь можно реализовать логику для перевода средств
            return;
        }

        if(accountService.closeAccount(selectedAccount.getAccountId())) {
            System.out.println("Аккаунт успешно удален.");
        }
        else {
            System.out.println("Ошибка при удалении аккаунта.");
        }

    }


    //---------------------------------------------------------------------------------------

    private void performTransaction() {
        System.out.println("Выберите тип транзакции:");
        System.out.println("1. Пополнить счет");
        System.out.println("2. Снять со счета");
        System.out.println("3. Перевести на другой счет");
        System.out.println("4. Обмен валюты");
        System.out.println("0. Назад");
        System.out.print("Введите номер операции: ");

        int operation = scanner.nextInt();
        scanner.nextLine();
        switch (operation) {
            case 1:
                deposit();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                transfer();
                break;
            case 4:
                exchange();
                break;
            case 0:
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
        }
    }


    private void deposit() {
        List<IAccountModel> userAccounts = accountService.findAccountsByUserId(currentUser.getUserId());
        if (userAccounts.isEmpty()) {
            System.out.println("У вас нет аккаунтов для пополнения.");
            return;
        }

        System.out.println("Выберите аккаунт для пополнения:");
        for (int i = 0; i < userAccounts.size(); i++) {
            IAccountModel account = userAccounts.get(i);
            System.out.println((i + 1) + ". Аккаунт ID: " + account.getAccountId()+ " Currency:"+account.getCurrency()+ " Balance:"+account.getBalance());
        }

        System.out.print("Введите номер аккаунта: ");
        int accountChoice = scanner.nextInt() - 1;
        if (accountChoice < 0 || accountChoice >= userAccounts.size()) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return;
        }

        IAccountModel selectedAccount = userAccounts.get(accountChoice);

        System.out.print("Введите сумму для пополнения: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Сумма пополнения должна быть больше нуля.");
            return;
        }

        transactionService.performTransaction(selectedAccount.getAccountId(), TransactionType.DEPOSIT, amount);
        System.out.println("Счет успешно пополнен на сумму " + amount);
    }

    private void withdraw() {
        List<IAccountModel> userAccounts = accountService.findAccountsByUserId(currentUser.getUserId());
        if (userAccounts.isEmpty()) {
            System.out.println("У вас нет аккаунтов для снятия средств.");
            return;
        }

        System.out.println("Выберите аккаунт для снятия средств:");
        for (int i = 0; i < userAccounts.size(); i++) {
            IAccountModel account = userAccounts.get(i);
            System.out.println((i + 1) + ". Аккаунт ID: " + account.getAccountId() + ", Баланс: " + account.getBalance());
        }

        System.out.print("Введите номер аккаунта: ");
        int accountChoice = scanner.nextInt() - 1;
        if (accountChoice < 0 || accountChoice >= userAccounts.size()) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return;
        }

        IAccountModel selectedAccount = userAccounts.get(accountChoice);

        System.out.print("Введите сумму для снятия: ");
        double amount = scanner.nextDouble();

        if (amount <= 0 || amount > selectedAccount.getBalance()) {
            System.out.println("Неверная сумма для снятия.");
            return;
        }

        transactionService.performTransaction(selectedAccount.getAccountId(), TransactionType.WITHDRAWAL, amount);
        System.out.println("Со счета снято " + amount + " единиц.");
    }

    private void transfer() {
        int accountIdFrom, accountIdTo;
        if (currentUser.getRole() == UserRole.ADMIN) {
            System.out.println("Выберите аккаунт отправителя:");
            accountIdFrom = selectAccount();

            System.out.println("Выберите аккаунт получателя:");
            int toAccount = selectAccount();
            if(toAccount>0) {
                accountIdTo = toAccount;
            }else {
                return;
            }
        } else {
            accountIdFrom = selectUserAccount();
            System.out.println("Выберите аккаунт получателя:");
            int toAccount = selectAccount();
            if(toAccount>0) {
                accountIdTo = toAccount;
            }else {
                return;
            }
        }

        System.out.print("Введите сумму для перевода: ");
        double amount = scanner.nextDouble();

        if (transactionService.transferFunds(accountIdFrom, accountIdTo, amount)) {
            System.out.println("Перевод успешно выполнен.");
        } else {
            System.out.println("Не удалось выполнить перевод.");
        }
    }

    public void exchange() {
        try {
            System.out.println("Выберите счет для снятия средств:");
            int accountIdFrom = selectUserAccount(null);
            if (accountIdFrom == -1) return;

            System.out.println("Выберите счет для зачисления средств:");
            int accountIdTo = selectUserAccount(accountIdFrom);
            if (accountIdTo == -1) return;

            System.out.print("Введите сумму для обмена: ");
            double amount = Double.parseDouble(scanner.nextLine());

            //TODO нужно брать из базы
            System.out.print("Введите обменный курс: ");
            double exchangeRate = Double.parseDouble(scanner.nextLine());

            transactionService.exchangeCurrency(accountIdFrom, accountIdTo, amount, exchangeRate);
            System.out.println("Обмен валюты выполнен успешно.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка при обмене валюты.");
        }
    }


    //TODO реализовать выбор аккаунта получателя
    private int selectAccount() {
        System.out.print("Введите идентификатор аккаунта: ");
        return scanner.nextInt();
    }

    //TODO улучшить выбора текущего пользователя
    private int selectUserAccount() {
        List<IAccountModel> userAccounts = accountService.findAccountsByUserId(currentUser.getUserId());
        if (userAccounts.isEmpty()) {
            System.out.println("У вас нет аккаунтов.");
            return -1;
        }

        System.out.println("Выберите аккаунт:");
        for (int i = 0; i < userAccounts.size(); i++) {
            IAccountModel account = userAccounts.get(i);
            System.out.println((i + 1) + ". Аккаунт ID: " + account.getAccountId() + ", Валюта: " + account.getCurrency().getName());
        }

        System.out.print("Введите номер аккаунта: ");
        int accountChoice = scanner.nextInt() - 1;
        if (accountChoice < 0 || accountChoice >= userAccounts.size()) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return -1;
        }

        return userAccounts.get(accountChoice).getAccountId();
    }

    private int selectUserAccount(Integer excludeAccountId) {
        List<IAccountModel> userAccounts = accountService.findAccountsByUserId(currentUser.getUserId());
        if (userAccounts.isEmpty()) {
            System.out.println("У вас нет аккаунтов.");
            return -1;
        }

        System.out.println("Выберите аккаунт:");
        int index = 1;
        Map<Integer, Integer> indexToAccountIdMap = new HashMap<>();
        for (IAccountModel account : userAccounts) {
            if (excludeAccountId == null || account.getAccountId() != excludeAccountId) {
                System.out.println(index + ". Аккаунт ID: " + account.getAccountId() + ", Валюта: " + account.getCurrency().getName());
                indexToAccountIdMap.put(index, account.getAccountId());
                index++;
            }
        }

        if (indexToAccountIdMap.isEmpty()) {
            System.out.println("Нет доступных аккаунтов для выбора.");
            return -1;
        }

        System.out.print("Введите номер аккаунта: ");
        int accountChoice = scanner.nextInt();
        scanner.nextLine();

        if (!indexToAccountIdMap.containsKey(accountChoice)) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return -1;
        }

        return indexToAccountIdMap.get(accountChoice);
    }


    //---------------------------------------------------------------------------------------
    private void viewTransactionHistory() {
        System.out.println("1. Просмотреть историю транзакций по конкретному аккаунту");
        System.out.println("2. Просмотреть историю всех моих транзакций");
        System.out.println("0. Назад");
        System.out.print("Выберите опцию: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                viewAccountTransactionHistory();
                break;
            case 2:
                viewUserTransactionHistory();
                break;
            case 0:
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void viewAccountTransactionHistory() {
        System.out.print("Введите идентификатор аккаунта: ");
        int accountId = selectUserAccount();
        List<ITransactionModel> transactions = transactionService.getTransactionHistoryByAccountId(accountId);
        printTransactions(transactions);
    }

    private void viewUserTransactionHistory() {
        List<ITransactionModel> transactions = transactionService.getTransactionHistoryByUserId(currentUser.getUserId());
        printTransactions(transactions);
    }

    private void printTransactions(List<ITransactionModel> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("Транзакции не найдены.");
            return;
        }
        for (ITransactionModel transaction : transactions) {
            System.out.println("Транзакция ID: " + transaction.getTransactionId() +
                    ", Тип: " + transaction.getType() +
                    ", Сумма: " + transaction.getAmount() +
                    ", Дата: " + transaction.getTransactionDate());
        }
    }
    //---------------------------------------------------------------------------------------

    private void manageCurrencies() {
        System.out.println("Управление валютами:");
        System.out.println("1. Просмотр всех валют");
        System.out.println("2. Добавить новую валюту");
        System.out.println("0. Назад");
        System.out.print("Выберите опцию: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                viewAllCurrencies();
                break;
            case 2:
                addNewCurrency();
                break;
            case 0:
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
        }
    }

    private void viewAllCurrencies() {
        List<ICurrencyModel> currencies = currencyService.getAllCurrencies();
        if (currencies.isEmpty()) {
            System.out.println("Валюты не найдены.");
            return;
        }
        for (ICurrencyModel currency : currencies) {
            System.out.println("Код валюты: " + currency.getCurrencyCode() + ", Название: " + currency.getName());
        }
    }

    private void addNewCurrency() {
        System.out.print("Введите код новой валюты: ");
        String code = scanner.next();
        System.out.print("Введите название валюты: ");
        String name = scanner.next();

        ICurrencyModel newCurrency = new CurrencyModel(code, name);
        currencyService.addCurrency(newCurrency);
        System.out.println("Валюта добавлена: " + newCurrency);
    }



    //---------------------------------------------------------------------------------------

    private void setCurrencyRates() {
        List<ICurrencyModel> currencies = currencyService.getAllCurrencies();
        if (currencies.isEmpty()) {
            System.out.println("Валюты для обновления не найдены.");
            return;
        }

        System.out.println("Выберите валюту для обновления курса:");
        for (int i = 0; i < currencies.size(); i++) {
            ICurrencyModel currency = currencies.get(i);
            System.out.println((i + 1) + ". " + currency.getName() + " (" + currency.getCurrencyCode() + ")");
        }

        System.out.print("Введите номер валюты: ");
        int currencyChoice = scanner.nextInt() - 1;
        if (currencyChoice < 0 || currencyChoice >= currencies.size()) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return;
        }

        ICurrencyModel selectedCurrency = currencies.get(currencyChoice);

        System.out.print("Введите новый курс для " + selectedCurrency.getName() + ": ");
        double newRate = scanner.nextDouble();

        ICurrencyRateModel currencyRate = new CurrencyRateModel(selectedCurrency.getCurrencyCode(), newRate, convertLocalDateToDate(LocalDate.now()));
        currencyService.updateCurrencyRate(currencyRate);
        System.out.println("Курс валюты обновлен.");
    }

    //---------------------------------------------------------------------------------------

    private void logoutUser()
    {
        currentUser = null;
    }
    //---------------------------------------------------------------------------------------

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    //---------------------------------------------------------------------------------------


/*
    // Реализация метода для показа меню входа
    @Override
    public void showLoginMenu() {
        System.out.println("=== Меню входа ===");
        System.out.println("1. Введите имя пользователя");
        System.out.println("2. Вернуться в главное меню");
        handleLoginMenuInput();
    }

    // Реализация метода для показа меню пользователя
    @Override
    public void showUserMenu(int userId) {
        System.out.println("=== Меню пользователя ===");
        System.out.println("1. Просмотр профиля");
        System.out.println("2. Просмотр баланса");
        System.out.println("3. Пополнение счета");
        System.out.println("4. Снятие средств");
        System.out.println("5. Открытие нового счета");
        System.out.println("6. Закрытие счета");
        System.out.println("7. Просмотр истории операций");
        System.out.println("8. Обмен валют");
        System.out.println("9. Выйти из системы");
        handleUserMenuInput(userId);
    }

    // Реализация метода для показа меню Администратора
    @Override
    public void showAdminMenu(int userId) {
        System.out.println("=== Меню администратора ===");
        System.out.println("1. Изменение курса валюты");
        System.out.println("2. Добавление/удаление валюты");
        System.out.println("3. Просмотр операций пользователя");
        System.out.println("4. Просмотр операций по валюте");
        System.out.println("5. Назначение другого пользователя администратором");
        System.out.println("6. Регистрация нового пользователя");
        System.out.println("7. Выйти из системы");
        handleAdminMenuInput(userId);
    }

    // Метод для обработки ввода в главном меню
    private void handleMainMenuInput() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                showLoginMenu();
                break;
            case 2://Регистрация нового пользователя
                registerNewUser();
                break;
            case 3:
                System.out.println("До свидания!");
                System.exit(0);
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showMainMenu();
        }
    }

    // Метод для обработки ввода в меню входа в систему
    private void handleLoginMenuInput() {
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleLoginInput();
                break;
            case 2:
                showMainMenu();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showLoginMenu();
        }
    }

    // Метод для обработки ввода в меню пользователя
    private void handleUserMenuInput(int userId) {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1://Просмотр профиля
                handleViewUserProfile();
                break;
            case 2://Просмотр баланса
                viewBalance(userId);
                break;
            case 3://Пополнение счета
                handleDeposit();
                break;
            case 4://Снятие средств
                handleWithdrawal();
                break;
            case 5://Открытие нового счета
                handleCreateAccount(userId);
                break;
            case 6://Закрытие счета
                handleCloseAccount();
                break;
            case 7://Просмотр истории операций
                handleViewTransactionHistory(userId);
                break;
            case 8://Обмен валют
                handleCurrencyExchange();
                break;
            case 9://Выйти из системы
                currentUser = null;
                showMainMenu();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                handleUserMenuInput(userId);
        }
    }

    // Метод для обработки ввода в меню Администратора
    private void handleAdminMenuInput(int userId) {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1://Изменение курса валюты
                handleUpdateCurrencyRate();
                break;
            case 2://Добавление/удаление валюты
                addOrRemoveCurrency();
                break;
            case 3://Просмотр операций пользователя
                viewUserTransactions();
                break;
            case 4://Просмотр операций по валюте
                viewCurrencyTransactions();
                break;
            case 5://Назначение другого пользователя администратором
                appointAdministrator();
                break;
            case 6://Регистрация нового пользователя
                registerNewUser();
                break;
            case 7://Выйти из системы
                currentUser = null;
                showMainMenu();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                handleAdminMenuInput(userId);
        }
    }

    // Метод для обработки просмотра профиля клиента
    private void handleViewUserProfile() {
        IUserModel user = userService.getUserById(currentUser.getUserId());
        if (user != null) {
            System.out.println("Информация о клиенте:");
            System.out.println("Имя: " + user.getName());
            System.out.println("Email: " + user.getEmail());

        } else {
            System.out.println("Пользователь не найден.");
        }

        // После просмотра профиля, показываем снова меню пользователя
        showUserMenu(currentUser.getUserId());
    }

    // Метод для обработки пополнения депозита
    private void handleDeposit() {
        System.out.println("Введите идентификатор счета для пополнения депозита:");
        int accountId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите сумму для пополнения депозита:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        // Вызов метода пополнения депозита
        accountService.deposit(accountId, amount);
    }

    // Метод для обработки снятия средств
    private void handleWithdrawal() {
        System.out.println("Введите идентификатор счета для снятия средств:");
        int accountId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите сумму для снятия средств:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        // Вызов метода снятия средств
        accountService.withdraw(accountId, amount);
    }

    // Метод для обработки обмена валюты
    private void handleCurrencyExchange() {
        System.out.println("Введите идентификатор счета, с которого вы хотите обменять валюту:");
        int accountIdFrom = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите идентификатор счета, на который вы хотите обменять валюту:");
        int accountIdTo = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите сумму для обмена:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        // Вызов метода обмена валюты
        transactionService.exchangeCurrency(accountIdFrom, accountIdTo, amount);
    }

    // Метод для обработки открытия нового счета
    private void handleCreateAccount(int userId) {
        System.out.println("Выберите валюту для нового счета:");

        // Выводим список доступных валют
        List<ICurrencyModel> currencies = currencyService.getAllCurrencies();
        for (int i = 0; i < currencies.size(); i++) {
            System.out.println((i + 1) + ". " + currencies.get(i).getCurrencyCode() + " - " + currencies.get(i).getName());
        }

        int currencyChoice = scanner.nextInt();
        scanner.nextLine();

        // Проверяем, что выбор находится в допустимых пределах
        if (currencyChoice >= 1 && currencyChoice <= currencies.size()) {
            // Получаем выбранную валюту
            ICurrencyModel selectedCurrency = currencies.get(currencyChoice - 1);

            // Вызываем метод открытия нового счета
            accountService.createAccount(userId, selectedCurrency);
        } else {
            System.out.println("Неверный выбор валюты.");
        }
    }

    // Метод для обработки закрытия счета
    private void handleCloseAccount() {
        System.out.println("Введите идентификатор счета для закрытия:");
        int accountIdToClose = scanner.nextInt();

        // вызов метода закрытия счета
        accountService.closeAccount(accountIdToClose);
    }

    // Метод для обработки просмотра истории операций
    private void handleViewTransactionHistory(int userId) {
        List<ITransactionModel> transactions = transactionService.getTransactionHistory(userId);
        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("История операций:");

            for (ITransactionModel transaction : transactions) {
                System.out.println("Тип операции: " + transaction.getType());
                System.out.println("Сумма: " + transaction.getAmount());
                System.out.println("Дата: " + transaction.getTransactionDate());
                System.out.println("-----------------------------");
            }

        } else {
            System.out.println("Нет доступных операций для просмотра.");
        }

        // После просмотра истории операций, показываем снова меню пользователя
        showUserMenu(userId);
    }

    // Метод для обработки изменения курса валюты
    private void handleUpdateCurrencyRate() {
        // Введите код валюты, для которой нужно изменить курс
        System.out.println("Введите код валюты (например, USD): ");
        String currencyCode = scanner.next();

        // Получите текущий курс валюты
        ICurrencyRateModel currentRate = currencyService.getCurrencyRate(currencyCode);

        if (currentRate != null) {
            // Запросите новый курс у пользователя
            System.out.println("Текущий курс валюты " + currencyCode + ": " + currentRate.getRate());
            System.out.println("Введите новый курс валюты: ");
            double newRate = scanner.nextDouble();

            // Создайте новую модель курса и обновите его
            ICurrencyRateModel newCurrencyRate = new CurrencyRateModel(currencyCode, newRate, new Date());
            currencyService.updateCurrencyRate(newCurrencyRate);
        } else {
            System.out.println("Курс валюты с кодом " + currencyCode + " не найден.");
        }

        // После обновления курса валюты, показываем снова меню администратора
        showAdminMenu(currentUser.getUserId());
    }

    // Метод реализация добавления/удаления валюты
    private void addOrRemoveCurrency() {
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить новую валюту");
        System.out.println("2. Удалить существующую валюту");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleAddCurrency();
                break;
            case 2:
                handleRemoveCurrency();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showAdminMenu(currentUser.getUserId());
        }
    }

    // Метод для обработки добавления новой валюты
    private void handleAddCurrency() {
        System.out.println("Введите код новой валюты:");
        String currencyCode = scanner.nextLine();

        System.out.println("Введите название новой валюты:");
        String currencyName = scanner.nextLine();

        // Создание новой валюты с использованием вашего класса CurrencyModel
        ICurrencyModel newCurrency = new CurrencyModel(currencyCode, currencyName);

        // Сохранение новой валюты в репозитории
        currencyService.addCurrency(newCurrency);

        System.out.println("Новая валюта успешно добавлена.");
        showAdminMenu(currentUser.getUserId());
    }

    // Метод для обработки удаления существующей валюты
    private void handleRemoveCurrency() {
        System.out.println("Введите код валюты, которую нужно удалить:");
        String currencyCode = scanner.nextLine();

        // Удаление валюты из репозитория
        currencyService.removeCurrency(currencyCode);

        System.out.println("Валюта успешно удалена.");
        showAdminMenu(currentUser.getUserId());
    }

    // Метод для обработки просмотра операций пользователя
    private void viewUserTransactions() {
        System.out.println("Введите идентификатор пользователя:");
        int userId = scanner.nextInt();
        scanner.nextLine();
        // Получение списка транзакций для пользователя
        List<ITransactionModel> userTransactions = transactionService.getTransactionHistoryByUserId(userId);

        if (userTransactions.isEmpty()) {
            System.out.println("Для указанного пользователя нет операций.");
        } else {
            System.out.println("Операции пользователя с идентификатором " + userId + ":");
            for (ITransactionModel transaction : userTransactions) {
                System.out.println(transaction);
            }
        }
        showUserMenu(userId);
    }

    // Метод для обработки просмотра операций по валюте
    private void viewCurrencyTransactions() {
        System.out.println("Введите код валюты:");
        String currencyCode = scanner.nextLine();

        // Получение списка транзакций для валюты
        List<ITransactionModel> currencyTransactions = transactionService.getTransactionHistoryByCurrencyCode(currencyCode);

        if (currencyTransactions.isEmpty()) {
            System.out.println("Для указанной валюты нет операций.");
        } else {
            System.out.println("Операции по валюте с кодом " + currencyCode + ":");
            for (ITransactionModel transaction : currencyTransactions) {
                System.out.println(transaction);
            }
        }

        // После просмотра операций, показываем снова меню пользователя
        showUserMenu(currentUser.getUserId());
    }

    // Метод для обработки назначения другого пользователя администратором
    private void appointAdministrator() {
        System.out.println("Введите идентификатор пользователя, которого вы хотите назначить администратором:");
        int userIdToAppoint = scanner.nextInt();
        scanner.nextLine();
        // Вызов метода сервиса пользователя для изменения роли на администратора
        userService.setUserAdmin(userIdToAppoint);

        System.out.println("Пользователь успешно назначен администратором.");

        // После назначения администратора, показываем снова меню администратора
        showAdminMenu(currentUser.getUserId());
    }

    // Метод для обработки ввода имени пользователя и пароля

    private void handleLoginInput() {
        System.out.println("Введите имя пользователя (Ваш E-mail):");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        // Вызов метода входа по имени пользователя и паролю
        IUserModel user = userService.login(username, password);
        currentUser = user;
        if (user != null) {
            // Вход успешен, показываем соответствующее меню
            if (user.getRole() == UserRole.ADMIN) {
                showAdminMenu(user.getUserId());
                //handleAdminMenuInput(user.getUserId());
            } else {
                showUserMenu(user.getUserId());
                //handleUserMenuInput(user.getUserId());
            }
        } else {
            System.out.println("Неверный email или пароль.");
            showLoginMenu();
        }
    }


    //Метод просмотра баланса пользователя
    private void viewBalance(int userId) {
        // Поиск счета пользователя по его идентификатору
        IAccountModel account = accountService.getAccountById(userId);

        if (account != null) {
            // Вывод баланса
            System.out.println("Баланс счета: " + account.getBalance());
        } else {
            System.out.println("Счет пользователя не найден.");
        }
    }

    private void generateUsers() {
        userService.generateFakeUsers();
    }

    private boolean registerNewUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите имя пользователя:");
        String name = scanner.nextLine();

        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();

        System.out.println("Введите пароль пользователя:");
        String password = scanner.nextLine();

        var result = userService.registerUser(name, email, password);
        if(result){
            System.out.println("Пользователь успешно зарегистрирован.");
            return true;
        } else {
            System.out.println("Ошибка ввода данных.");
            return false;
        }

    }

*/
}

