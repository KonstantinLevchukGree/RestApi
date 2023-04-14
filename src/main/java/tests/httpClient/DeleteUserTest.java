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

public class DeleteUserTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Delete")
    @Story(value = "Delete one User")
    @Description(value = "Test checks Delete one User")
    @Test
    public void checkDeleteUser() {
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        assertFalse(users.contains(users), "User not deleted");
    }

    @Epic(value = "User")
    @Feature(value = "Delete")
    @Story(value = "Delete User With Required Fields")
    @Description(value = "Test checks Delete User With Required Fields")
    @Test
    public void checkDeleteUserWithRequiredFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();

        assertFalse(users.contains(users), "User not deleted");
        List<String> zipCodesAfterDeleteUser = ApplicationClient.getZipCodes();
        assertTrue(availableZipCodes.containsAll(zipCodesAfterDeleteUser), "Zip code not returned");
    }

    @Epic(value = "User")
    @Feature(value = "Delete")
    @Story(value = "Delete User Without Required Fields")
    @Description(value = "Test checks Delete User Without Required Fields")
    @Test
    public void checkDeleteUserWithoutRequiredFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setSex(null);
        femaleUser.setName(null);

        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_CONFLICT);
        List<User> users = ApplicationClient.getUsers();
        assertFalse(users.contains(users), "User deleted");
    }
}
