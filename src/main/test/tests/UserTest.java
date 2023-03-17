package tests;

import configurate.RequestApi;
import objects.User;
import org.junit.jupiter.api.Test;
import utils.api.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends BaseTest {
    @Test
    public void checkAddUserWithAllFields() {
        List<String> availableZipCodes = RequestApi.getListZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        RequestApi.createUser(ApiUtils.getJsonStringFromObject(user));
        List<User> users = RequestApi.getUsers(user);
        assertEquals(user, users.get(0), "User not added");
        List<String> availableZipCodesAfterAddUser = RequestApi.getListZipCodes();
        assertFalse(availableZipCodesAfterAddUser.contains(users.get(0).getZipCode()), "Zip code not removed");
    }

    @Test
    public void checkAddUserWithRequiredFields() {
        RequestApi.createUser(ApiUtils.getJsonStringFromObject(user));
        List<User> users = RequestApi.getUsers(user);
        assertEquals(user, users.get(0), "User not added");
    }

    @Test
    public void checkAddUserWithIncorrectZipCode() {
        user.setZipCode(incorrectZipCode);
        RequestApi.createUser(ApiUtils.getJsonStringFromObject(user));
        List<User> users = RequestApi.getUsers(user);
        assertTrue(users.contains(users), "User added");
    }

    @Test
    public void checkAddNotUniqueUser() {
        List<String> availableZipCodes = RequestApi.getListZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        RequestApi.createUser(ApiUtils.getJsonStringFromObject(user));
        user.setZipCode(availableZipCodes.get(1));
        RequestApi.createUser(ApiUtils.getJsonStringFromObject(user));
        List<User> users = RequestApi.getUsers(user);
        //Same users added to application. Bug
        assertEquals(1, users.size(), "Not unique added to application");
    }
}
