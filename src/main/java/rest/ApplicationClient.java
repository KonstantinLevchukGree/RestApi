package rest;

import configurate.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import utils.ApiUtils;

import java.util.List;

import static tests.BaseTest.applicationData;

public class ApplicationClient {
    public static String getBodyZipCodes(int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(applicationData.getProperty("api.zip.code"));
        return ApiUtils.fromResponseToString(response, httpStatus);
    }

    public static List<String> getZipCodes(int httpStatus) {
        String zipCodes = getBodyZipCodes(httpStatus);
        return ApiUtils.getListFromJson(zipCodes);
    }

    public static List<String> expandZipCodes(String zipCodes, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.zip.codes.expand"), zipCodes);
        String responseBody = ApiUtils.fromResponseToString(response, httpStatus);
        return ApiUtils.getListFromJson(responseBody);
    }

    public static void createUser(String userJson, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendPost(applicationData.getProperty("api.users"), userJson);
        ApiUtils.fromResponseToString(response, httpStatus);
    }

    public static List<User> getUsers(User user, int httpStatus) {
        CloseableHttpResponse response = CloseableHttpClient.sendGet(applicationData.getProperty("api.users"), user);
        String responseBody = ApiUtils.fromResponseToString(response, httpStatus).replace("[", "")
                .replace("]", "").trim();
        return ApiUtils.getUserFromJson(responseBody);
    }
}
