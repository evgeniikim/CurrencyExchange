package service;

import interfaces.IAccountModel;
import interfaces.IAccountRepository;
import interfaces.IAccountService;
import interfaces.ICurrencyModel;
import model.AccountModel;

public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(int userId, ICurrencyModel currency){

        int newAccountId = generateAccountId();
        IAccountModel newAccount = new AccountModel(newAccountId, userId, currency, 0.0);
        accountRepository.saveAccount(newAccount);
        System.out.println("Счет успешно создан. Идентификатор счета: " + newAccountId);

    }
    public void closeAccount(int accountId){

        IAccountModel account = accountRepository.findAccountById(accountId);
        if (account != null) {
            accountRepository.deleteAccount(accountId);
            System.out.println("Счет успешно закрыт.");
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");

        }

    }
    public IAccountModel getAccountById(int accountId){
        return accountRepository.findAccountById(accountId);
    }
    public void deposit(int accountId, double amount){
        IAccountModel account = accountRepository.findAccountById(accountId);
        if (account != null) {
            account.deposit(amount);
            accountRepository.updateAccount(account);
            System.out.println("Депозит успешно внесен.");
        } else {
            System.out.println("Счет с указанным идентификатором не найден.");
        }
    }
    public void withdraw(int accountId, double amount){
            IAccountModel account = accountRepository.findAccountById(accountId);

            if (account != null) {
                // Снятие средств со счета
                if (account.getBalance() >= amount) {
                    account.withdraw(amount);
                    // Обновление счета в репозитории
                    accountRepository.updateAccount(account);
                    System.out.println("Средства успешно сняты.");
                } else {
                    System.out.println("Недостаточно средств на счете.");
                }
            } else {
                System.out.println("Счет с указанным идентификатором не найден.");
            }
    }
        private int generateAccountId() {
            return accountRepository.getAllAccounts().size() + 1;
        }
}