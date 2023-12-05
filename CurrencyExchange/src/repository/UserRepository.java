package repository;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import Helper.DataHelper;
import com.google.gson.reflect.TypeToken;
import interfaces.*;
import exception.*;
import model.UserModel;

public class UserRepository implements IUserRepository {

    private Map<Integer, IUserModel> users; // Хранилище пользователей

    public UserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void addUser(IUserModel user) {
        var userId = getNextUserId();
        users.put(userId, user);
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

    @Override
    public int getNextUserId() {
        return users.keySet()
                .stream()
                .max(Integer::compare)
                .map(maxId-> maxId +1)
                .orElse(1);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return users.values()
                .stream()
                .anyMatch(user -> email.equals(user.getEmail()));
    }

    @Override
    public int getUsersCount() {
        return users.size();
    }

    @Override
    public int saveToFile() {
        try {
            DataHelper.exportDataToJson("users.json", users);
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }

    @Override
    public int loadFromFile() {
        try {
            Type type = new TypeToken<Map<Integer, UserModel>>(){}.getType();
            Map<Integer, IUserModel> loadUsers = DataHelper.importDataFromJson("users.json", type);
            if(loadUsers!=null) {
                users = loadUsers;
            }
            return 0;
        } catch (IOException e) {
            ExceptionHandling.handleException(e);
            return -1; // Код ошибки
        }
    }
}
