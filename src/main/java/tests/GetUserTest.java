package tests;

import org.junit.jupiter.api.Test;
import rest.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetUserTest extends BaseTest {
    @Test
    public void checkAllUsers() {
        List<User> users = ApplicationClient.getUsers();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        List<User> usersAfterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size() + 1, usersAfterAddUser.size(), "Not all users received");
    }

    @Test
    public void checkUsersOlderAge() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        user.setAge(user.getAge() + 1);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        List<User> users = ApplicationClient.getFilterUsers(user.getAge() - 1, applicationData.getProperty("request.parameter.older"));

        for (User value : users) {
            assertTrue(user.getAge() >= value.getAge(), "Filter not working");
        }
    }

    @Test
    public void checkUsersYoungerAge() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        user.setAge(user.getAge() - 1);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        List<User> users = ApplicationClient.getFilterUsers(user.getAge() + 1, applicationData.getProperty("request.parameter.younger"));

        for (User value : users) {
            assertTrue(user.getAge() <= value.getAge(), "Filter not working");
        }
    }

    @Test
    public void checkUsersSex() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        user.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        user.setSex(applicationData.getProperty("user.sex.male"));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(user));
        List<User> users = ApplicationClient.getFilterUsers(user.getSex(), applicationData.getProperty("request.parameter.sex"));
        //Gender filter not working Bug
        for (User value : users) {
            assertEquals(user.getSex(), value.getSex(), "Filter not working");
        }
    }
}
