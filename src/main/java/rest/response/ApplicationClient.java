package rest.response;

import configurate.CloseableHttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import rest.UpdateUser;
import rest.User;
import utils.ApiUtils;

import java.io.File;
import java.util.List;

import static tests.httpClient.BaseTest.appData;

public class ApplicationClient {
    public static String getBodyZipCodes() {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(appData.getProperty("api.zip.code"));
        return ApiUtils.fromResponseToString(response, HttpStatus.SC_OK);
    }

    public static List<String> getZipCodes() {
        String zipCodes = getBodyZipCodes();
        return ApiUtils.getListFromJson(zipCodes);
    }

    public static List<String> expandZipCodes(String zipCodes) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(appData.getProperty("api.zip.codes.expand"), zipCodes);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_CREATED);
        return ApiUtils.getListFromJson(responseBody);
    }

    public static void createUser(String userJson) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(appData.getProperty("api.users"), userJson);
        ApiUtils.fromResponseToString(response, HttpStatus.SC_CREATED);
    }

    public static void createUser(String userJson, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(appData.getProperty("api.users"), userJson);
        ApiUtils.fromResponseToString(response, httpStatus);
    }

    public static List<User> getUsers() {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(appData.getProperty("api.users"));
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_OK).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }

    public static List<User> getFilterUsers(int age, String parameter) {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(appData.getProperty("api.users"), parameter, age);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_OK).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }

    public static List<User> getFilterUsers(String sex, String parameter) {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(appData.getProperty("api.users"), parameter, sex);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_OK).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }

    public static void updateUser(User oldUser, User newUser) {
        updateUser(oldUser, newUser, HttpStatus.SC_OK);
    }

    public static void updateUser(User oldUser, User newUser, int httpStatus) {
        UpdateUser users = new UpdateUser(newUser, oldUser);
        String json = ApiUtils.fromObjectToJson(users);
        CloseableHttpResponse response = CloseableHttpClient.sendPut(appData.getProperty("api.users"), json);
        ApiUtils.fromResponseToString(response, httpStatus);
    }

    public static void deleteUser(String json) {
        deleteUser(json, HttpStatus.SC_NO_CONTENT);
    }

    public static void deleteUser(String json, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendDelete(appData.getProperty("api.users"), json);
        String responseBody = ApiUtils.fromResponseToString(response, httpStatus);
        ApiUtils.getUserFromJson(responseBody);
    }

    public static String uploadUser(File file) {
        return uploadUser(file, HttpStatus.SC_CREATED);
    }

    public static String uploadUser(File file, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(appData.getProperty("api.users.upload"), file);
        return ApiUtils.fromResponseToString(response, httpStatus).replace("Number of users = ", "").trim();
    }
}
