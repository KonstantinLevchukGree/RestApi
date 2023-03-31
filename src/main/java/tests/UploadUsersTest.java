package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;
import rest.User;
import utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadUsersTest extends BaseTest {


    @Test
    public void checkUpdateUsers() {
        List<User> usersFromUpload = new ArrayList<>();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        usersFromUpload.add(femaleUser);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setZipCode(availableZipCodes.get(1));
        usersFromUpload.add(maleUser);
        ApiUtils.usersToFile(ApiUtils.fromObjectToJson(usersFromUpload));

        String updatedUsers = ApplicationClient.uploadUser(ApiUtils.usersFromFile());
        assertEquals(usersFromUpload, ApplicationClient.getUsers(), "Users not updated");
        assertEquals(Integer.parseInt(updatedUsers), usersFromUpload.size(), "Number of updated users does not match");
    }

    @Test
    public void checkUpdateUsersWithIncorrectZipCode() {
        List<User> usersFromUpload = new ArrayList<>();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        usersFromUpload.add(femaleUser);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setZipCode(incorrectZipCode);
        usersFromUpload.add(maleUser);
        ApiUtils.usersToFile(ApiUtils.fromObjectToJson(usersFromUpload));
        //should be 424
        ApplicationClient.uploadUser(ApiUtils.usersFromFile(), HttpStatus.SC_FAILED_DEPENDENCY);
        assertEquals(usersFromUpload, ApplicationClient.getUsers(), "Users updated");
    }

    @Test
    public void checkUpdateUsersNoRequiredField() {
        List<User> usersFromUpload = new ArrayList<>();
        List<String> availableZipCodes = ApplicationClient.getZipCodes();
        femaleUser.setZipCode(availableZipCodes.get(0));
        usersFromUpload.add(femaleUser);
        ApplicationClient.createUser(ApiUtils.fromObjectToJson(femaleUser));
        maleUser.setZipCode(availableZipCodes.get(1));
        maleUser.setSex(null);
        usersFromUpload.add(maleUser);
        ApiUtils.usersToFile(ApiUtils.fromObjectToJson(usersFromUpload));
        //should be 409
        ApplicationClient.uploadUser(ApiUtils.usersFromFile(), HttpStatus.SC_CONFLICT);
        assertEquals(usersFromUpload, ApplicationClient.getUsers(), "Users not updated");
    }
}