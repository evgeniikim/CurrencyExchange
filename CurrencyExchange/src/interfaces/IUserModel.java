package interfaces;


import java.io.Serializable;

public interface IUserModel extends Serializable {
    int getUserId();
    String getName();
    String getEmail();
    String getPassword();
    void setName(String name);
    void setEmail(String email);
    void setPassword(String password);
}