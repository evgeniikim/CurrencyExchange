package generator;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import interfaces.UserRole;
import model.*;

public class UserGenerator {

    private static final Random random = new Random();
    private static final String[] FIRST_NAMES = {"Lukas", "Leon", "Maximilian", "Felix", "Paul", "Sophie", "Marie", "Laura", "Anna", "Julia"};
    private static final String[] LAST_NAMES = {"Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker", "Schulz", "Hoffmann"};

    private static final String domain = "@ait-tr.de";
    //private static int userIdCounter = 1;

    public static UserModel generateUser() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String name = firstName + " " + lastName;
        String email = (firstName.toLowerCase() + "." + lastName.toLowerCase() + random.nextInt(100) + domain);
        String password = generateRandomPassword();

        UserModel user = new UserModel(0, name, email, password);
        user.setRole(UserRole.CLIENT);
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

    public static List<UserModel> generateStaticUsers() {
        List<UserModel> users = new ArrayList<>();
        { //Admin
          String firstName = "Admin";
          String lastName = "Admin";
          String name = firstName + " " + lastName;
          String email = "admin"+domain;
          String password = "admin";
          UserModel user = new UserModel(0, name, email, password);
          user.setRole(UserRole.ADMIN);
          users.add(user);
        }
        { //User1
            String firstName = "User1";
            String lastName = "User1";
            String name = firstName + " " + lastName;
            String email = "user1"+domain;
            String password = "user";
            UserModel user = new UserModel(0, name, email, password);
            user.setRole(UserRole.CLIENT);
            users.add(user);
        }
        { //User2
            String firstName = "User2";
            String lastName = "User2";
            String name = firstName + " " + lastName;
            String email = "user2"+domain;
            String password = "user";
            UserModel user = new UserModel(0, name, email, password);
            user.setRole(UserRole.CLIENT);
            users.add(user);
        }
        { //User3
            String firstName = "User3";
            String lastName = "User3";
            String name = firstName + " " + lastName;
            String email = "user3"+domain;
            String password = "user";
            UserModel user = new UserModel(0, name, email, password);
            user.setRole(UserRole.CLIENT);
            users.add(user);
        }
        return users;
    }

    public static List<UserModel> generateUsersBatch(int numberOfUsers) {
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            users.add(generateUser());
        }
        return users;
    }
}
