package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserTest extends BaseTest {
    @Test
    public void checkUpdateUser() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        newUser.setZipCode(availableZipCodes.get(1));
        ApplicationClient.updateUser(user, newUser);
        List<User> users = ApplicationClient.getUsers();
        assertTrue(users.contains(newUser), "User not updated");
    }

    @Test
    public void checkUpdateUserWithIncorrectZipCode() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        List<User> users = ApplicationClient.getUsers();
        newUser.setZipCode(incorrectZipCode);
        newUser.setName("Unique");

        ApplicationClient.updateUser(user, newUser, HttpStatus.SC_FAILED_DEPENDENCY);
        List<User> afterUpdateUser = ApplicationClient.getUsers();
        assertFalse(afterUpdateUser.contains(newUser), "User updated");
        //Deletes the users Bug
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }

    @Test
    public void checkUpdateUserWithoutFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        List<User> users = ApplicationClient.getUsers();
        newUser.setName(null);
        ApplicationClient.updateUser(user, newUser, HttpStatus.SC_CONFLICT);

        List<User> afterUpdateUser = ApplicationClient.getUsers();
        assertFalse(afterUpdateUser.contains(newUser), "User updated");
        //Deleted the users Bug
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }
}
