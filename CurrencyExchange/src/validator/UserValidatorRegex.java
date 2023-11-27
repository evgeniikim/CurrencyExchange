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
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    @Override
    public boolean validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        if (REQUIRE_SPECIAL_CHAR && !password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }
        if (REQUIRE_DIGITS && !password.matches(".*\\d.*")) {
            return false;
        }
        return true;
    }
}
