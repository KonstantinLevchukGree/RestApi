package tests.httpClient;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Update")
    @Story(value = "One Users")
    @Description(value = "Test checks update Users")
    @Test
    public void checkUpdateUser() {
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.updateUser(femaleUser, maleUser);
        List<User> users = ApplicationClient.getUsers();
        assertTrue(users.contains(maleUser), "User not updated");
    }

    @Epic(value = "User")
    @Feature(value = "Update")
    @Story(value = "Incorrect ZipCode")
    @Description(value = "Test checks update Users With Incorrect ZipCode")
    @Test
    public void checkUpdateUserWithIncorrectZipCode() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        maleUser.setZipCode(availableZipCodes.get(0) + availableZipCodes.get(0));

        ApplicationClient.updateUser(femaleUser, maleUser, HttpStatus.SC_FAILED_DEPENDENCY);
        List<User> afterUpdateUser = ApplicationClient.getUsers();
        assertFalse(afterUpdateUser.contains(maleUser), "User updated");
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }

    @Epic(value = "User")
    @Feature(value = "Update")
    @Story(value = "Without Required Fields")
    @Description(value = "Test checks update Users With Fields")
    @Test
    public void checkUpdateUserWithoutRequiredFields() {
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        maleUser.setName(null);
        maleUser.setSex(null);
        ApplicationClient.updateUser(femaleUser, maleUser, HttpStatus.SC_CONFLICT);

        List<User> afterUpdateUser = ApplicationClient.getUsers();
        assertFalse(afterUpdateUser.contains(maleUser), "User updated");
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }
}
