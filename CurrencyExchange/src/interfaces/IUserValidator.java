package interfaces;

public interface IUserValidator {
    boolean validateEmail(String email);
    boolean validatePassword(String password);
}