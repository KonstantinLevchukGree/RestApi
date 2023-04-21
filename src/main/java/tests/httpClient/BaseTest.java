package tests.httpClient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import rest.response.ApplicationClient;
import rest.http5.AuthenticationClient;
import rest.User;
import rest.factory.client.ReaderClient;
import rest.factory.client.WriterClient;
import utils.ApiUtils;
import utils.PropertyUtil;

import java.util.Properties;

public class BaseTest {
    public static String readerToken;
    public static String writerToken;
    public static User femaleUser;
    public static User maleUser;
    public static Properties appData = PropertyUtil.getProperties("app.properties");

    @BeforeAll
    public static void initAll(){
        readerToken = AuthenticationClient.getToken(new ReaderClient(appData.getProperty("reader.user.scope")));
        writerToken = AuthenticationClient.getToken(new WriterClient(appData.getProperty("writer.user.scope")));
        ApplicationClient.expandZipCodes(appData.getProperty("zip.codes"));
    }
    @BeforeEach
    public void init() {
        femaleUser = User.builder()
                .name(appData.getProperty("user.name"))
                .age(Integer.parseInt(appData.getProperty("user.age")))
                .sex(appData.getProperty("user.sex.female"))
                .build();
        maleUser = User.builder()
                .name(appData.getProperty("user.name"))
                .age(Integer.parseInt(appData.getProperty("user.age")))
                .sex(appData.getProperty("user.sex.male"))
                .build();
        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(maleUser));
    }
    @AfterEach
    public void clearTestData(){
        if(ApplicationClient.getUsers().size()==0){
            ApplicationClient.deleteUser(ApiUtils.fromObjectToJson(femaleUser));
        }
    }
}
