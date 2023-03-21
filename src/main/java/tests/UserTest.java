package tests;

import org.apache.http.HttpStatus;
import rest.ApplicationClient;
import rest.User;
import org.junit.jupiter.api.Test;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends BaseTest {
    @Test
    public void checkAddUserWithAllFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user),HttpStatus.SC_CREATED);

        List<User> users = ApplicationClient.getUsers(user,HttpStatus.SC_OK);
        assertEquals(user, users.get(0), "User not added");
        List<String> availableZipCodesAfterAddUser = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        assertFalse(availableZipCodesAfterAddUser.contains(users.get(0).getZipCode()), "Zip code not removed");
    }

    @Test
    public void checkAddUserWithRequiredFields() {
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user),HttpStatus.SC_CREATED);
        List<User> users = ApplicationClient.getUsers(user,HttpStatus.SC_OK);
        assertEquals(user, users.get(0), "User not added");
    }

    @Test
    public void checkAddUserWithIncorrectZipCode() {
        user.setZipCode(incorrectZipCode);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user),HttpStatus.SC_CREATED);
        List<User> users = ApplicationClient.getUsers(user,HttpStatus.SC_OK);
        assertTrue(users.contains(users), "User added");
    }

    @Test
    public void checkAddNotUniqueUser() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user),HttpStatus.SC_CREATED);
        user.setZipCode(availableZipCodes.get(1));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user),HttpStatus.SC_CREATED);

        List<User> users = ApplicationClient.getUsers(user,HttpStatus.SC_OK);
        //Same users added to application. Bug
        assertEquals(1, users.size(), "Not unique added to application");
    }
}
