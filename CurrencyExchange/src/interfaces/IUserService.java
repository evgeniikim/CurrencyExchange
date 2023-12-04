package interfaces;

public interface IUserService {
    void registerUser(IUserModel user);
    boolean registerUser(String name, String email, String password);
    IUserModel login(String email, String password);
    void updateUserProfile(IUserModel user);
    IUserModel getUserById(int userId);
    void setUserAdmin(int userId);
    void generateFakeUsers();
    void generateStaticUsers();
    void loadData();
    void saveData();
    int getUsersCount();
}