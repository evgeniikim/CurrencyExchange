package interfaces;

import Helper.DataHelper;
import exception.ExceptionHandling;

import java.io.IOException;

public interface IUserRepository extends IExportImportFile {
    void addUser(IUserModel user);
    IUserModel findUserByEmail(String email);
    IUserModel findUserById(int userId);
    void updateUser(IUserModel user);
    int getNextUserId();
    boolean userExistsByEmail(String email);
    int getUsersCount();
}