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
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setZipCode(availableZipCodes.get(1));
        ApplicationClient.updateUser(femaleUser, maleUser);
        List<User> users = ApplicationClient.getUsers();
        assertTrue(users.contains(maleUser), "User not updated");
    }

    @Test
    public void checkUpdateUserWithIncorrectZipCode() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        maleUser.setZipCode(incorrectZipCode);
        maleUser.setName("Unique");

        ApplicationClient.updateUser(femaleUser, maleUser, HttpStatus.SC_FAILED_DEPENDENCY);
        List<User> afterUpdateUser = ApplicationClient.getUsers();
        assertFalse(afterUpdateUser.contains(maleUser), "User updated");
        //Deletes the users Bug
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }

    @Test
    public void checkUpdateUserWithoutFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        maleUser.setName(null);
        ApplicationClient.updateUser(femaleUser, maleUser, HttpStatus.SC_CONFLICT);

        List<User> afterUpdateUser = ApplicationClient.getUsers();
        assertFalse(afterUpdateUser.contains(maleUser), "User updated");
        //Deleted the users Bug
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }
}
