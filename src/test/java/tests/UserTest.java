package tests;

import endPoint.Point;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import user.User;
import utils.api.ApiUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserTest extends BaseTest {

    @Test
    public void checkAddUserWithAllFields() {
        String zipCodesJson = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        List<String> zipCodesList = ApiUtils.getListFromJson(zipCodesJson);
        String zipCod = zipCodesList.get(0);
        user.setZipCode(zipCod);
        String userJson = ApiUtils.getJsonStringFromObject(user);
        ApiUtils.sendPost(writerToken, Point.USERS.getApiPoint(), userJson, HttpStatus.SC_CREATED);
        String userJsonFromApi = ApiUtils.sendGet(readerToken, Point.USERS.getApiPoint(), HttpStatus.SC_OK, user)
                .replace("[", "").replace("]", "").trim();
        List<User> userFromApi = ApiUtils.getUserFromJsonString(userJsonFromApi);
        assertEquals(user, userFromApi.get(0), "User not added");
        String allZipCodes = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        List<String> allZipCodesList = ApiUtils.getListFromJson(allZipCodes);
        assertFalse(allZipCodesList.contains(userFromApi.get(0).getZipCode()), "Zip code not removed");
    }

    @Test
    public void checkAddUserWithRequiredFields() {
        String userJson = ApiUtils.getJsonStringFromObject(user);
        ApiUtils.sendPost(writerToken, Point.USERS.getApiPoint(), userJson, HttpStatus.SC_CREATED);
        String userJsonFromApi = ApiUtils.sendGet(readerToken, Point.USERS.getApiPoint(), HttpStatus.SC_OK, user)
                .replace("[", "").replace("]", "").trim();
        List<User> userFromApi = ApiUtils.getUserFromJsonString(userJsonFromApi);
        assertEquals(user, userFromApi.get(0), "User not added");
    }

    @Test
    public void checkAddUserWithIncorrectZipCode() {
        user.setZipCode(incorrectZipCode);
        String userJson = ApiUtils.getJsonStringFromObject(user);
        ApiUtils.sendPost(writerToken, Point.USERS.getApiPoint(), userJson, HttpStatus.SC_FAILED_DEPENDENCY);
        String userJsonFromApi = ApiUtils.sendGet(readerToken, Point.USERS.getApiPoint(), HttpStatus.SC_OK, user)
                .replace("[", "").replace("]", "").trim();
        assertEquals("", userJsonFromApi, "User added");
    }

    @Test
    public void checkAddNotUniqueUser() {
        String userJson = ApiUtils.getJsonStringFromObject(user);
        ApiUtils.sendPost(writerToken, Point.USERS.getApiPoint(), userJson, HttpStatus.SC_CREATED);
        ApiUtils.sendPost(writerToken, Point.USERS.getApiPoint(), userJson, HttpStatus.SC_CREATED);
        String userJsonFromApi = ApiUtils.sendGet(readerToken, Point.USERS.getApiPoint(), HttpStatus.SC_OK, user)
                .replace("[", "").replace("]", "").trim();
        List<User> userFromApi = ApiUtils.getUserFromJsonString(userJsonFromApi);
        //Same users added to application. Bug
        assertEquals(1, userFromApi.size(), "Same users added to application");
    }
}
