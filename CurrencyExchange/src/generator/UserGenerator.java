package generator;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class UserGenerator {

    private static final Random random = new Random();
    private static final String[] FIRST_NAMES = {"Lukas", "Leon", "Maximilian", "Felix", "Paul", "Sophie", "Marie", "Laura", "Anna", "Julia"};
    private static final String[] LAST_NAMES = {"Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker", "Schulz", "Hoffmann"};

    private static int userIdCounter = 1;

    public static UserModel generateUser() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String name = firstName + " " + lastName;
        String email = (firstName.toLowerCase() + "." + lastName.toLowerCase() + random.nextInt(100) + "@ait-tr.de");
        String password = generateRandomPassword();

        UserModel user = new UserModel(userIdCounter++, name, email, password);
        return user;
    }

    private static String generateRandomPassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }

    public static List<UserModel> generateUsersBatch(int numberOfUsers) {
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            users.add(generateUser());
        }
        return users;
    }
}
