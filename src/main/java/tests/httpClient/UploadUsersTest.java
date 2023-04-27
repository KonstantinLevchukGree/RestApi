package tests.httpClient;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.response.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UploadUsersTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "Upload Users from File")
    @Story(value = "Users")
    @Description(value = "Test checks Upload Users from File")
    @Test
    public void checkUploadUsers() {
        List<User> usersFromUpload = new ArrayList<>();
        usersFromUpload.add(femaleUser);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        usersFromUpload.add(maleUser);

        ApiUtils.usersToFile(ApiUtils.fromObjectToJson(usersFromUpload));
        String updatedUsers = ApplicationClient.uploadUser(ApiUtils.usersFromFile());
        assertEquals(usersFromUpload, ApplicationClient.getUsers(), "Users not updated");
        assertEquals(Integer.parseInt(updatedUsers), usersFromUpload.size(), "Number of updated users does not match");
    }

    @Epic(value = "User")
    @Feature(value = "Upload Users from File")
    @Story(value = "Incorrect ZipCode")
    @Description(value = "Test checks Upload Users With Incorrect ZipCode")
    @Test
    public void checkUploadUsersWithIncorrectZipCode() {
        List<User> usersFromUpload = new ArrayList<>();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        usersFromUpload.add(femaleUser);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setZipCode(femaleUser.getZipCode() + femaleUser.getZipCode());
        usersFromUpload.add(maleUser);

        ApiUtils.usersToFile(ApiUtils.fromObjectToJson(usersFromUpload));
        ApplicationClient.uploadUser(ApiUtils.usersFromFile(), HttpStatus.SC_FAILED_DEPENDENCY);
        assertNotEquals(usersFromUpload, ApplicationClient.getUsers(), "Users updated");
    }

    @DisplayName("Test failed, Server cannot process the request")
    @Epic(value = "User")
    @Feature(value = "Upload Users from File")
    @Story(value = "No Required Field")
    @Description(value = "Test checks Upload Users No Required Field")
    @Test
    public void checkUploadUsersNoRequiredField() {
        List<User> usersFromUpload = new ArrayList<>();
        usersFromUpload.add(femaleUser);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setName(null);
        maleUser.setSex(null);
        usersFromUpload.add(maleUser);

        ApiUtils.usersToFile(ApiUtils.fromObjectToJson(usersFromUpload));
        ApplicationClient.uploadUser(ApiUtils.usersFromFile(), HttpStatus.SC_CONFLICT);
        assertNotEquals(usersFromUpload, ApplicationClient.getUsers(), "Users not updated");
    }
}
