package view;

import interfaces.IConsoleMenu;
import interfaces.ICurrencyRateModel;
import interfaces.ITransactionModel;
import interfaces.IUserModel;
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

    public ConsoleMenu(AccountService accountService, UserService userService, TransactionService transactionService, CurrencyService currencyService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.scanner = new Scanner(System.in);
    }

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
        System.out.println("2. Введите пароль");
        System.out.println("3. Вернуться в главное меню");
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
        System.out.println("6. Выйти из системы");
        handleAdminMenuInput(userId);
    }

    // Реализация метода для показа меню аккаунта
    @Override
    public void showAccountMenu(int accountId) {
        System.out.println("=== Меню аккаунта ===");
        System.out.println("1. Просмотр баланса");
        System.out.println("2. Совершить транзакцию");
        System.out.println("3. Вернуться в меню пользователя");
        handleAccountMenuInput();
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

    // Метод для обработки ввода в меню входа
    private void handleLoginMenuInput() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Введите имя пользователя");
                break;
            case 2:
                System.out.println("Введите пароль");
                break;
            case 3:
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
                handleCreateAccount();
                break;
            case 6://Закрытие счета
                handleCloseAccount();
                break;
            case 7://Просмотр истории операций
                handleViewTransactionHistory(currentUser.getUserId());
                break;

            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showUserMenu(currentUser.getUserId());
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

            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showAdminMenu(userId);

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
            case 6://Выйти из системы
                showMainMenu();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                handleAdminMenuInput(userId);
        }
    }

    // Метод для обработки ввода в меню аккаунта
    private void handleAccountMenuInput() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Просмотр баланса");
                break;
            case 2:
                System.out.println("Совершить транзакцию");
                handleTransactionMenu();
                break;
            case 3:
                showUserMenu(123); // Заменить 123 на фактический идентификатор пользователя
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showAccountMenu(456); // Заменить 456 на фактический идентификатор аккаунта
        }
    }

    // Метод для обработки ввода в меню транзакции
    private void handleTransactionMenu() {
        System.out.println("Выберите тип транзакции:");
        System.out.println("1. Пополнение счета");
        System.out.println("2. Снятие средств");
        System.out.println("3. Обмен валюты");

        int transactionChoice = scanner.nextInt();
        scanner.nextLine();

        switch (transactionChoice) {
            case 1:
                // Пополнения счета
                handleDeposit();
                break;
            case 2:
                // Снятия средств
                handleWithdrawal();
                break;
            case 3:
                // Обмена валюты
                handleCurrencyExchange();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
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
    private void handleCreateAccount() {
        System.out.println("Выберите валюту для нового счета:");
        // нужно добавить логику для выбора валюты, например, считывание кода валюты или ее названия

        // вызов метода открытия нового счета
        accountService.createAccount(userId, selectedCurrency); // Замените selectedCurrency на выбранную валюту
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
        showAdminMenu();
    }


}