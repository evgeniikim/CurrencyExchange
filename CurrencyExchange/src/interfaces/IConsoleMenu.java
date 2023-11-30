package interfaces;

public interface IConsoleMenu {
    void showMainMenu();
    void showLoginMenu();

    // Реализация метода для показа меню пользователя
    void showUserMenu(int userId);

    // Реализация метода для показа меню Администратора
    void showAdminMenu(int userId);

    void showAccountMenu(int accountId);
}

