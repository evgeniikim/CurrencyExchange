package view;

import interfaces.*;
import model.CurrencyModel;
import model.CurrencyRateModel;
import service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu implements IConsoleMenu {
    private final Scanner scanner;

    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final CurrencyService currencyService;
    private IUserModel currentUser;

    public ConsoleMenu(AccountService accountService, UserService userService, TransactionService transactionService, CurrencyService currencyService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
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

    // public void setCurrentUser(IUserModel user) {
    //     this.currentUser = user;
    // }

    // Реализация метода для показа главного меню
    @Override
    public void showMainMenu() {
        System.out.println("=== Главное меню ===");
        System.out.println("1. Вход");
        System.out.println("2. Выход");
        handleMainMenuInput();
    }

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
            case 2:
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
        List<ITransactionModel> userTransactions = transactionService.getTransactionHistory(userId);

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

        System.out.println("Введите ID пользователя:");
        int userId = scanner.nextInt();
        scanner.nextLine();

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
}