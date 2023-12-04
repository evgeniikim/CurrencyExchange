package service;

import generator.UserGenerator;
import interfaces.*;
import validator.*;
import model.*;

public class UserService implements IUserService {

    private IUserRepository userRepository; // Репозиторий для работы с пользователями
    private UserValidatorRegex userValidator; // Валидатор для проверки данных пользователя

    // Конструктор класса
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.userValidator = new UserValidatorRegex();
    }

    @Override
    public void registerUser(IUserModel user) {
        // Проверка входных данных и регистрация пользователя
        if (user != null && validateUser(user)) {
            userRepository.addUser(user);
            System.out.println("Пользователь успешно зарегистрирован.");
        } else {
            System.out.println("Ошибка при регистрации пользователя.");
        }
    }

    @Override
    public boolean registerUser(String name, String email, String password) {
        // Проверка входных данных и регистрация пользователя
        var user = new UserModel(0, name, email, password);
        if (validateUser(user)) {
            user.setRole(UserRole.CLIENT);
            userRepository.addUser(user);
            System.out.println("Пользователь успешно зарегистрирован.");
            return true;
        } else {
            System.out.println("Ошибка при регистрации пользователя.");
            return false;
        }
    }

    @Override
    public IUserModel login(String email, String password) {
        // Проверка учетных данных пользователя
        IUserModel user = userRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Успешный вход в систему.");
            return user;
        } else {
            System.out.println("Неверный email или пароль.");
            return null;
        }
    }

    @Override
    public void setUserAdmin(int userId) {
        // Находим пользователя по идентификатору
        IUserModel user = userRepository.findUserById(userId);

        // Проверка, найден ли пользователь
        if (user != null) {
            // Устанавливаем роль пользователя на администратора
            user.setRole(UserRole.ADMIN);

            // Обновляем профиль пользователя
            userRepository.updateUser(user);
            System.out.println("Профиль пользователя обновлен. Пользователь назначен администратором.");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }


    @Override
    public void updateUserProfile(IUserModel user) {
        // Обновление профиля пользователя
        if (user != null && userRepository.findUserById(user.getUserId()) != null) {
            userRepository.updateUser(user);
            System.out.println("Профиль пользователя обновлен.");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }


    @Override
    public IUserModel getUserById(int userId) {
        // Получение информации о пользователе по ID
        return userRepository.findUserById(userId);
    }

    private boolean validateUser(IUserModel user) {
        // Валидация данных пользователя
        return user.getName() != null && !user.getName().isEmpty() &&
                userValidator.validateEmail(user.getEmail()) &&
                userValidator.validatePassword(user.getPassword());
    }

    @Override
    public void generateFakeUsers() {
        int usersAdded = 0;
        var fakeUsers = UserGenerator.generateUsersBatch(10);
        for (UserModel user : fakeUsers) {
            if(!userRepository.userExistsByEmail(user.getEmail()))
            {
                userRepository.addUser(user);
                usersAdded++;
            }
        }
        System.out.println("Добавлено фейковых пользователей: "+usersAdded);

    }

    @Override
    public void generateStaticUsers() {
        var staticUsers = UserGenerator.generateStaticUsers();
        int usersAdded = 9;
        for (UserModel user : staticUsers) {
            if(!userRepository.userExistsByEmail(user.getEmail()))
            {
                userRepository.addUser(user);
                usersAdded++;
            }
        }
        System.out.println("Добавлено системных пользователей: "+usersAdded);

    }

    @Override
    public int getUsersCount() {
      return userRepository.getUsersCount();
    }


    @Override
    public void loadData() {
        userRepository.loadFromFile();
    }
    @Override
    public void saveData() {
        userRepository.saveToFile();
    }
}
