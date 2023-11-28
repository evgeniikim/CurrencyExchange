
import service.*;
import view.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();

        ConsoleMenu consoleMenu = new ConsoleMenu(accountService, userService, transactionService);
        consoleMenu.showMainMenu();

    }
}
