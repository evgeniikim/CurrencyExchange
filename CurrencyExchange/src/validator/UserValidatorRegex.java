package validator;

import java.util.regex.Pattern;
import interfaces.*;
public class UserValidatorRegex implements IUserValidator {

    // Регулярное выражение для проверки email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Критерии для пароля (можно настроить по желанию)
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final boolean REQUIRE_SPECIAL_CHAR = true;
    private static final boolean REQUIRE_DIGITS = true;

    @Override
    public boolean validateEmail(String email) {
        var result = email != null && Pattern.matches(EMAIL_REGEX, email);
        System.out.println("Email не соответствует формату!");
        return result;
    }

    @Override
    public boolean validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            System.out.println("Password не соответствует формату!");
            return false;
        }
        if (REQUIRE_SPECIAL_CHAR && !password.matches(".*[!@#$%^&*()].*")) {
            System.out.println("Password должен содержать спец символы!");
            return false;
        }
        if (REQUIRE_DIGITS && !password.matches(".*\\d.*")) {
            System.out.println("Password должен содержать цифры!");
            return false;
        }
        return true;
    }
}
