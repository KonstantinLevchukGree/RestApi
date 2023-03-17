package tests;

import objects.Client;
import org.junit.jupiter.api.BeforeEach;
import objects.User;
import configurate.AuthenticationClient;
import utils.property.PropertyUtil;

import java.util.Properties;

public class BaseTest {
    public static String readerToken;
    public static String writerToken;
    public static User user;
    public static String incorrectZipCode;
    public static Properties applicationData = PropertyUtil.getProperties("applicationData.properties");

    @BeforeEach
    public void init() {
        Client writerClient = Client.builder()
                .userName(applicationData.getProperty("writer.user.name"))
                .userPassword(applicationData.getProperty("writer.user.password"))
                .grantType(applicationData.getProperty("writer.user.grant.type"))
                .userScope(applicationData.getProperty("writer.user.scope"))
                .build();
        Client readerClient = Client.builder()
                .userName(applicationData.getProperty("reader.user.name"))
                .userPassword(applicationData.getProperty("reader.user.password"))
                .grantType(applicationData.getProperty("reader.user.grant.type"))
                .userScope(applicationData.getProperty("reader.user.scope"))
                .build();
        user = User.builder()
                .name(applicationData.getProperty("user.name"))
                .age(Integer.parseInt(applicationData.getProperty("user.age")))
                .sex(applicationData.getProperty("user.sex"))
                .build();
        readerToken = AuthenticationClient.getToken(readerClient
                , applicationData.getProperty("token.port.name")
                , applicationData.getProperty("token.host.name")
                , applicationData.getProperty("token.scheme"));
        writerToken = AuthenticationClient.getToken(writerClient
                , applicationData.getProperty("token.port.name")
                , applicationData.getProperty("token.host.name")
                , applicationData.getProperty("token.scheme"));
        incorrectZipCode = applicationData.getProperty("user.incorrect.zip.code");
    }
}
