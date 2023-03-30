package tests;

import org.junit.jupiter.api.BeforeEach;
import rest.ApplicationClient;
import rest.AuthenticationClient;
import rest.User;
import utils.PropertyUtil;
import utils.SingletonReaderClient;
import utils.SingletonWriterClient;

import java.util.Properties;

public class BaseTest {
    public static String readerToken;
    public static String writerToken;

    public static User user;
    public static User newUser;
    public static String incorrectZipCode;
    public static Properties applicationData = PropertyUtil.getProperties("app.properties");

    @BeforeEach
    public void init() {
        user = User.builder()
                .name(applicationData.getProperty("user.name"))
                .age(Integer.parseInt(applicationData.getProperty("user.age")))
                .sex(applicationData.getProperty("user.sex.female"))
                .build();
        newUser = User.builder()
                .name(applicationData.getProperty("user.name"))
                .age(Integer.parseInt(applicationData.getProperty("user.age")))
                .sex(applicationData.getProperty("user.sex.male"))
                .build();
        readerToken = AuthenticationClient.getToken(SingletonReaderClient.getClient()
                , applicationData.getProperty("token.port.name")
                , applicationData.getProperty("token.host.name")
                , applicationData.getProperty("token.scheme"));
        writerToken = AuthenticationClient.getToken(SingletonWriterClient.getClient()
                , applicationData.getProperty("token.port.name")
                , applicationData.getProperty("token.host.name")
                , applicationData.getProperty("token.scheme"));
        incorrectZipCode = applicationData.getProperty("user.incorrect.zip.code");
        ApplicationClient.expandZipCodes(applicationData.getProperty("zip.codes"));
    }
}
