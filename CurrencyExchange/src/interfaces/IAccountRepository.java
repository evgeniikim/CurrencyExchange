package interfaces;


import java.util.List;

public interface IAccountRepository extends IExportImportFile{
    void saveAccount(IAccountModel account);
    IAccountModel findAccountById(int accountId);
    List<IAccountModel> findAccountsByUserId(int userId);
    void deleteAccount(int accountId);
    void updateAccount(IAccountModel account);
    int getNextAccountId();

}