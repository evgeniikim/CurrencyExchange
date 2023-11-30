package interfaces;


public interface IAccountRepository extends IExportImportFile{
    void saveAccount(IAccountModel account);
    IAccountModel findAccountById(int accountId);
    void deleteAccount(int accountId);
    void updateAccount(IAccountModel account);
    int getNextAccountId();

}