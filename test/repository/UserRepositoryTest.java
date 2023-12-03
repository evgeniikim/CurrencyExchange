package repository;

import interfaces.IUserModel;
import model.UserModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void addUser() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        assertNotNull(repository.findUserById(1));
    }

    @Test
    void findUserByEmail() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        IUserModel foundUser = repository.findUserByEmail("user@example.com");

        assertNotNull(foundUser, "User should not be null");
        // Дополнительные проверки на соответствие ожидаемым значениям
        assertEquals("user@example.com", foundUser.getEmail(), "Email should match");
        assertEquals("John Doe", foundUser.getName(), "Name should match");
    }

    @Test
    void findUserById() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        assertEquals(user, repository.findUserById(1));
    }

    @Test
    void updateUser() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        user.setName("Updated Name");
        repository.updateUser(user);

        assertEquals("Updated Name", repository.findUserById(1).getName());
    }

    @Test
    void saveToFile() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        assertEquals(0, repository.saveToFile());
    }

    @Test
    void loadFromFile() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        assertEquals(0, repository.loadFromFile());
    }

/*
    @Test
    void saveToFileAndLoadFromFile() {
        UserRepository repository = new UserRepository();
        IUserModel user = new UserModel(1, "John Doe", "user@example.com", "GhKdk153!");
        repository.addUser(user);

        repository.saveToFile();

        // Create a new repository to simulate a fresh instance
        UserRepository loadedRepository = new UserRepository();
        loadedRepository.loadFromFile();

        IUserModel loadedUser = loadedRepository.findUserById(1);

        assertNotNull(loadedUser, "Loaded user should not be null");
        assertEquals(user, loadedUser, "Loaded user should match added user");
    }*/
}
