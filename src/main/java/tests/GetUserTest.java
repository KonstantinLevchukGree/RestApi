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
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> usersAfterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size() + 1, usersAfterAddUser.size(), "Not all users received");
    }

    @Test
    public void checkUsersOlderAge() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setAge(femaleUser.getAge() + 1);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getAge() - 1, applicationData.getProperty("request.parameter.older"));

        for (User value : users) {
            assertTrue(femaleUser.getAge() >= value.getAge(), "Filter not working");
        }
    }

    @Test
    public void checkUsersYoungerAge() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setAge(femaleUser.getAge() - 1);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getAge() + 1, applicationData.getProperty("request.parameter.younger"));

        for (User value : users) {
            assertTrue(femaleUser.getAge() <= value.getAge(), "Filter not working");
        }
    }

    @Test
    public void checkUsersSex() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setSex(applicationData.getProperty("user.sex.male"));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getSex(), applicationData.getProperty("request.parameter.sex"));
        //Gender filter not working Bug
        for (User value : users) {
            assertEquals(femaleUser.getSex(), value.getSex(), "Filter not working");
        }
    }
}
