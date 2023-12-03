package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import interfaces.UserRole;

class UserModelTest {

    private UserModel user;

    @BeforeEach
    void setUp() {
        user = new UserModel(1, "John Doe", "john.doe@example.com", "password123");
    }

    @Test
    void getUserId() {
        assertEquals(1, user.getUserId());
    }

    @Test
    void getName() {
        assertEquals("John Doe", user.getName());
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void getPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    void setUserId() {
        user.setUserId(2);
        assertEquals(2, user.getUserId());
    }

    @Test
    void setName() {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    void setEmail() {
        user.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", user.getEmail());
    }

    @Test
    void setPassword() {
        user.setPassword("newpassword456");
        assertEquals("newpassword456", user.getPassword());
    }

    @Test
    void testToString() {
        String expectedString = "UserModel{userId=1, name='John Doe', email='john.doe@example.com', password='password123'}";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void setRole() {
        user.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void getRole() {
        assertEquals(UserRole.CLIENT, user.getRole());
    }
    @Test
    void getRole_defaultRole_shouldReturnClient() {
        assertEquals(UserRole.CLIENT, user.getRole());
    }

    @Test
    void setRole_validRole_shouldSetRoleSuccessfully() {
        user.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void toString_validUserModel_shouldNotThrowException() {
        assertDoesNotThrow(() -> user.toString());
    }

    @Test
    void equals_differentUserModel_shouldReturnFalse() {
        UserModel differentUser = new UserModel(2, "Jane Doe", "jane.doe@example.com", "password456");
        assertFalse(user.equals(differentUser));
    }

    @Test
    void equals_nullUserModel_shouldReturnFalse() {
        assertFalse(user.equals(null));
    }
}
