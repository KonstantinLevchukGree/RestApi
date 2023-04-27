package tests.httpClient;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import rest.response.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Get")
    @Story(value = "All Users")
    @Description(value = "Test checks Users")
    @Test
    public void checkAllUsers() {
        List<User> users = ApplicationClient.getUsers();
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
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setAge(femaleUser.getAge() + 1);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(maleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getAge(), appData.getProperty("request.parameter.older"));

        for (User value : users) {
            assertTrue(value.getAge() >= femaleUser.getAge(), "Filter not working");
        }
    }

    @Epic(value = "User")
    @Feature(value = "Filter")
    @Story(value = "Users Younger Age")
    @Description(value = "Test checks Users Younger Age")
    @Test
    public void checkUsersYoungerAge() {
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setAge(femaleUser.getAge() - 1);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(maleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getAge(), appData.getProperty("request.parameter.younger"));

        for (User value : users) {
            assertTrue(value.getAge() <= femaleUser.getAge(), "Filter not working");
        }
    }

    @Epic(value = "User")
    @Feature(value = "Filter")
    @Story(value = "All Users")
    @Description(value = "Test checks Users Sex")
    @Test
    public void checkUsersSex() {
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(maleUser));
        List<User> users = ApplicationClient.getFilterUsers(femaleUser.getSex(), appData.getProperty("request.parameter.sex"));

        for (User value : users) {
            assertEquals(femaleUser.getSex(), value.getSex(), "Filter not working");
        }
    }
}
