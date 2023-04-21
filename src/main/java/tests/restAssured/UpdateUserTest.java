package tests.restAssured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import rest.User;
import rest.restAssured.ApplicationRestClient;
import tests.httpClient.BaseTest;
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
        ApplicationRestClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationRestClient.updateUser(femaleUser, maleUser);
        List<User> users = ApplicationRestClient.getUsers();
        assertTrue(users.contains(maleUser), "User not updated");
    }

    @Epic(value = "User")
    @Feature(value = "Update")
    @Story(value = "Incorrect ZipCode")
    @Description(value = "Test checks update Users With Incorrect ZipCode")
    @Test
    public void checkUpdateUserWithIncorrectZipCode() {
        List<String> availableZipCodes = ApplicationRestClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationRestClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationRestClient.getUsers();
        maleUser.setZipCode(availableZipCodes.get(0) + availableZipCodes.get(0));

        ApplicationRestClient.updateUser(femaleUser, maleUser, HttpStatus.SC_FAILED_DEPENDENCY);
        List<User> afterUpdateUser = ApplicationRestClient.getUsers();
        assertFalse(afterUpdateUser.contains(maleUser), "User updated");
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }

    @Epic(value = "User")
    @Feature(value = "Update")
    @Story(value = "Without Required Fields")
    @Description(value = "Test checks update Users With Fields")
    @Test
    public void checkUpdateUserWithoutRequiredFields() {
        ApplicationRestClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationRestClient.getUsers();
        maleUser.setName(null);
        maleUser.setSex(null);
        ApplicationRestClient.updateUser(femaleUser, maleUser, HttpStatus.SC_CONFLICT);

        List<User> afterUpdateUser = ApplicationRestClient.getUsers();
        assertFalse(afterUpdateUser.contains(maleUser), "User updated");
        assertEquals(users.size(), afterUpdateUser.size(), "User deleted");
    }
}
