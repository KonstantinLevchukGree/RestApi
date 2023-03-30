package rest;

import configurate.CloseableHttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import utils.ApiUtils;

import java.io.File;
import java.util.List;

import static tests.BaseTest.applicationData;

public class ApplicationClient {
    public static String getBodyZipCodes() {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(applicationData.getProperty("api.zip.code"));
        return ApiUtils.fromResponseToString(response, HttpStatus.SC_CREATED);
    }

    public static List<String> getZipCodes() {
        String zipCodes = getBodyZipCodes();
        return ApiUtils.getListFromJson(zipCodes);
    }

    public static List<String> expandZipCodes(String zipCodes) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.zip.codes.expand"), zipCodes);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_CREATED);
        return ApiUtils.getListFromJson(responseBody);
    }

    public static void createUser(String userJson) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.users"), userJson);
        ApiUtils.fromResponseToString(response, HttpStatus.SC_CREATED);
    }

    public static void createUser(String userJson, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.users"), userJson);
        ApiUtils.fromResponseToString(response, httpStatus);
    }

    public static List<User> getUsers() {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(applicationData.getProperty("api.users"));
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_OK).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }

    public static List<User> getFilterUsers(int age, String parameter) {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(applicationData.getProperty("api.users"), parameter, age);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_OK).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }

    public static List<User> getFilterUsers(String sex, String parameter) {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(applicationData.getProperty("api.users"), parameter, sex);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_OK).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }

    public static void updateUser(User oldUser, User newUser) {
        String json = "{" + "\"userNewValues\"" + ":" + ApiUtils.fromObjectToJson(newUser)
                + "," + "\"userToChange\"" + ":" + ApiUtils.fromObjectToJson(oldUser) + "}";
        CloseableHttpResponse response = CloseableHttpClient.sendPut(applicationData.getProperty("api.users"), json);
        ApiUtils.fromResponseToString(response, HttpStatus.SC_OK);
    }

    public static void updateUser(User oldUser, User newUser, int httpStatus) {
        String json = "{" + "\"userNewValues\"" + ":" + ApiUtils.fromObjectToJson(newUser)
                + "," + "\"userToChange\"" + ":" + ApiUtils.fromObjectToJson(oldUser) + "}";
        CloseableHttpResponse response = CloseableHttpClient.sendPut(applicationData.getProperty("api.users"), json);
        ApiUtils.fromResponseToString(response, httpStatus);
    }

    public static void deleteUser(String json) {
        CloseableHttpResponse response = CloseableHttpClient.sendDelete(applicationData.getProperty("api.users"), json);
        String responseBody = ApiUtils.fromResponseToString(response, HttpStatus.SC_NO_CONTENT);
        ApiUtils.getUserFromJson(responseBody);
    }

    public static void deleteUser(String json, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendDelete(applicationData.getProperty("api.users"), json);
        String responseBody = ApiUtils.fromResponseToString(response, httpStatus);
        ApiUtils.getUserFromJson(responseBody);
    }

    public static String uploadUser(File file) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.users.upload"), file);
        return ApiUtils.fromResponseToString(response, HttpStatus.SC_CREATED).replace("Number of users = ", "").trim();
    }

    public static String uploadUser(File file, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.users.upload"), file);
        return ApiUtils.fromResponseToString(response, httpStatus).replace("Number of users = ", "").trim();
    }
}
