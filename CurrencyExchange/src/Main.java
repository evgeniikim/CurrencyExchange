
import interfaces.IAccountRepository;
import interfaces.ICurrencyService;
import interfaces.ITransactionRepository;
import repository.AccountRepository;
import repository.CurrencyRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.*;
import view.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository(accountRepository);
        CurrencyRepository currencyRepository = new CurrencyRepository();

        AccountService accountService = new AccountService(accountRepository);
        UserService userService = new UserService(userRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository);
        CurrencyService currencyService = new CurrencyService(currencyRepository);



        ConsoleMenu consoleMenu = new ConsoleMenu(accountService, userService, transactionService, currencyService);
        consoleMenu.start();

    }
}