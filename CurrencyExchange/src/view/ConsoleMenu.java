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
        System.out.println("2. Просмотр транзакций");
        System.out.println("3. Выйти из системы");
        handleUserMenuInput();
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
    private void handleUserMenuInput() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Просмотр профиля");
                break;
            case 2:
                System.out.println("Просмотр транзакций");
                break;
            case 3:
                showMainMenu();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                showUserMenu(123); // Замените 123 на фактический идентификатор пользователя
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