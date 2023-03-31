package tests;

import org.apache.http.HttpStatus;
import rest.ApplicationClient;
import rest.User;
import org.junit.jupiter.api.Test;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest {
    @Test
    public void checkAddUserWithAllFields() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getUsers();
        assertEquals(femaleUser, users.get(0), "User not added");
        List<String> availableZipCodesAfterAddUser = ApplicationClient.getZipCodes();
        assertEquals(availableZipCodes.size()-1,availableZipCodesAfterAddUser.size(), "Zip code not removed");
    }

    @Test
    public void checkAddUserWithRequiredFields() {
        List<User> users = ApplicationClient.getUsers();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> afterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size()+1, afterAddUser.size(), "User not added");
    }

    @Test
    public void checkAddUserWithIncorrectZipCode() {
        List<User> users = ApplicationClient.getUsers();
        femaleUser.setZipCode(incorrectZipCode);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_FAILED_DEPENDENCY);
        List<User> afterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size(), afterAddUser.size(), "User added");
    }

    @Test
    public void checkAddNotUniqueUser() {
        List<User> users = ApplicationClient.getUsers();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        //should be 400 and added not unique user
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser), HttpStatus.SC_BAD_REQUEST);
        List<User> afterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size()+1, afterAddUser.size(), "Not unique added to application");
    }
}
