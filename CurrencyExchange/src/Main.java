
import repository.AccountRepository;
import repository.UserRepository;
import service.*;
import view.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        TransactionService transactionService = new TransactionService();
        CurrencyService currencyService = new CurrencyService();

        ConsoleMenu consoleMenu = new ConsoleMenu(accountService, userService, transactionService, currencyService);
        consoleMenu.showMainMenu();

    }
}