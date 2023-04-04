package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetUserTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Get")
    @Story(value = "All Users")
    @Description(value = "Test checks Users")
    @Test
    public void checkAllUsers() {
        List<User> users = ApplicationClient.getUsers();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> usersAfterAddUser = ApplicationClient.getUsers();
        assertEquals(users.size() + 1, usersAfterAddUser.size(), "Not all users received");
    }

    @Epic(value = "User")
    @Feature(value = "Filter")
    @Story(value = "Users Older Age")
    @Description(value = "Test checks Users Older Age")
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

    @Epic(value = "User")
    @Feature(value = "Filter")
    @Story(value = "Users Younger Age")
    @Description(value = "Test checks Users Younger Age")
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

    @DisplayName("Test failed, Filter not working")
    @Epic(value = "User")
    @Feature(value = "Filter")
    @Story(value = "All Users")
    @Description(value = "Test checks Users Sex")
    @Test
    public void checkUsersSex() {
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        femaleUser.setSex(applicationData.getProperty("user.sex.male"));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getSex(), applicationData.getProperty("request.parameter.sex"));
        for (User value : users) {
            assertEquals(femaleUser.getSex(), value.getSex(), "Filter not working");
        }
    }
}
