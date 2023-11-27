package interfaces;

public interface IUserService {
    void registerUser(IUserModel user);
    IUserModel login(String email, String password);
    void updateUserProfile(IUserModel user);
    IUserModel getUserById(int userId);
}