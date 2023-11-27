package interfaces;


public interface IUserModel {
    int getUserId();
    String getName();
    String getEmail();
    String getPassword();
    void setName(String name);
    void setEmail(String email);
    void setPassword(String password);
}