package configurate;

import objects.User;
import rest.Request;
import rest.Response;
import utils.api.ApiUtils;

import java.util.List;

import static tests.BaseTest.applicationData;

public class RequestApi {
    public static String getJsonZipCodes() {
        return Response.closeResponse(Request.sendGet(applicationData.getProperty("api.zip.code")));
    }

    public static List<String> getListZipCodes() {
        String zipCodes = Response.closeResponse(Request.sendGet(applicationData.getProperty("api.zip.code")));
        return ApiUtils.getListFromJson(zipCodes);
    }

    public static List<String> expandZipCodes(String zipCodes) {
        String expandZipCodes = Response.closeResponse(Request.sendPost(applicationData.getProperty("api.zip.codes.expand"), zipCodes));
        return ApiUtils.getListFromJson(expandZipCodes);
    }

    public static void createUser(String userJson) {
        Response.closeResponse(Request.sendPost(applicationData.getProperty("api.users"), userJson));
    }

    public static List<User> getUsers(User user) {
        String jsonUsers = Response.closeResponse(Request.sendGet(applicationData.getProperty("api.users"), user))
                .replace("[", "").replace("]", "").trim();
        return ApiUtils.getUserFromJsonString(jsonUsers);
    }
}
