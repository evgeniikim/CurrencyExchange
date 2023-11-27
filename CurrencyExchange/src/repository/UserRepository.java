package repository;


import java.util.HashMap;
import java.util.Map;
import interfaces.*;

public class UserRepository implements IUserRepository {

    private Map<Integer, IUserModel> users; // Хранилище пользователей

    public UserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void addUser(IUserModel user) {
        users.put(user.getUserId(), user);
        System.out.println("Пользователь сохранен: " + user);
    }

    @Override
    public IUserModel findUserByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IUserModel findUserById(int userId) {
        return users.get(userId);
    }

    @Override
    public void updateUser(IUserModel user) {
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            System.out.println("Пользователь обновлен: " + user);
        } else {
            System.out.println("Пользователь для обновления не найден.");
        }
    }
}
