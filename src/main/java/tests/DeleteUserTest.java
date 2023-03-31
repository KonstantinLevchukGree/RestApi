package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteUserTest extends BaseTest {
    @Test
    public void checkDeleteUser() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        assertFalse(users.contains(users), "User not deleted");
    }

    @Test
    public void checkDeleteUserWithRequiredFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setAge(0);

        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        assertFalse(users.contains(users), "User not deleted");
        List<String> zipCodesAfterDeleteUser = ApplicationClient.getZipCodes();
        assertTrue(availableZipCodes.containsAll(zipCodesAfterDeleteUser), "Zip code not returned");
    }

    @Test
    public void checkDeleteUserWithoutRequiredFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setSex(null);

        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_CONFLICT);
        List<User> users = ApplicationClient.getUsers();
        assertFalse(users.contains(users), "User deleted");
    }
}
