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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Create")
    @Story(value = "All Fields")
    @Description(value = "Test checks Add User With All Fields")
    @Test
    public void checkAddUserWithAllFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        assertEquals(femaleUser, users.get(0), "User not added");
        List<String> availableZipCodesAfterAddUser = ApplicationClient.getZipCodes();
        assertEquals(availableZipCodes.size() - 1, availableZipCodesAfterAddUser.size(), "Zip code not removed");
    }

    @Epic(value = "User")
    @Feature(value = "Create")
    @Story(value = "Required Fields")
    @Description(value = "Test checks Add User With Required Fields")
    @Test
    public void checkAddUserWithRequiredFields() {
        List<User> users = ApplicationClient.getUsers();
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> afterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size() + 1, afterAddUser.size(), "User not added");
    }

    @Epic(value = "User")
    @Feature(value = "Create")
    @Story(value = "Incorrect ZipCode")
    @Description(value = "Test checks Add User With Incorrect ZipCode")
    @Test
    public void checkAddUserWithIncorrectZipCode() {
        List<User> users = ApplicationClient.getUsers();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0) + availableZipCodes.get(1));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_FAILED_DEPENDENCY);
        List<User> afterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size(), afterAddUser.size(), "User added");
    }

    @Epic(value = "User")
    @Feature(value = "Create")
    @Story(value = "Not Unique User")
    @Description(value = "Test checks Add Not Unique User")
    @Test
    public void checkAddNotUniqueUser() {
        List<User> users = ApplicationClient.getUsers();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_BAD_REQUEST);
        List<User> afterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size() + 1, afterAddUser.size(), "Not unique added to application");
    }
}
