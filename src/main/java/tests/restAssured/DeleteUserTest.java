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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUserTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Delete")
    @Story(value = "Delete one User")
    @Description(value = "Test checks Delete one User")
    @Test
    public void checkDeleteUser() {
        ApplicationRestClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationRestClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationRestClient.getUsers();
        assertFalse(users.contains(users), "User not deleted");
    }

    @Epic(value = "User")
    @Feature(value = "Delete")
    @Story(value = "Delete User With Required Fields")
    @Description(value = "Test checks Delete User With Required Fields")
    @Test
    public void checkDeleteUserWithRequiredFields() {
        List<String> availableZipCodes = ApplicationRestClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationRestClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationRestClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationRestClient.getUsers();

        assertFalse(users.contains(users), "User not deleted");
        List<String> zipCodesAfterDeleteUser = ApplicationRestClient.getZipCodes();
        assertTrue(availableZipCodes.containsAll(zipCodesAfterDeleteUser), "Zip code not returned");
    }

    @Epic(value = "User")
    @Feature(value = "Delete")
    @Story(value = "Delete User Without Required Fields")
    @Description(value = "Test checks Delete User Without Required Fields")
    @Test
    public void checkDeleteUserWithoutRequiredFields() {
        List<String> availableZipCodes = ApplicationRestClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationRestClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setSex(null);
        femaleUser.setName(null);

        ApplicationRestClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_CONFLICT);
        List<User> users = ApplicationRestClient.getUsers();
        assertFalse(users.contains(users), "User deleted");
    }
}
