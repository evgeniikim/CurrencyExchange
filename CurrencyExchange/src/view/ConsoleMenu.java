package view;

import interfaces.IConsoleMenu;
import service.*;

import java.util.Scanner;

public class ConsoleMenu implements IConsoleMenu {
    private final Scanner scanner;

    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;

    public ConsoleMenu(AccountService accountService, UserService userService, TransactionService transactionService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
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
                viewProfile(userId);
                break;
            case 2://Просмотр баланса
                viewBalance(userId);
                break;
            case 3://Пополнение счета
                accountService.deposit(userId);
                break;
            case 4://Снятие средств
                accountService.withdraw(userId);
                break;
            case 5://Открытие нового счета
                openNewAccount(userId);
                break;
            case 6://Закрытие счета
               accountService.closeAccount(userId);
                break;
            case 7://Просмотр истории операций
                viewTransactionHistory(userId);
                break;
            case 8://Обмен валют
                exchangeCurrency(userId);
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
                changeCurrencyRate();
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
                break;
            case 3:
                showUserMenu(123); // Заменить 123 на фактический идентификатор пользователя
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showAccountMenu(456); // Заменить 456 на фактический идентификатор аккаунта
        }
    }
}