package interfaces;

public interface IUserRepository {
    void saveUser(IUserModel user);
    IUserModel findUserByEmail(String email);
    IUserModel findUserById(int userId);
    void updateUser(IUserModel user);
}