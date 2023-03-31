package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import rest.ApplicationClient;
import rest.AuthenticationClient;
import rest.User;
import rest.factory.client.ReaderClient;
import rest.factory.client.WriterClient;
import utils.PropertyUtil;

import java.util.Properties;

public class BaseTest {
    public static String readerToken;
    public static String writerToken;
    public static User femaleUser;
    public static User maleUser;
    public static String incorrectZipCode;
    public static Properties applicationData = PropertyUtil.getProperties("app.properties");

    @BeforeAll
    public static void initAll(){
        readerToken = AuthenticationClient.getToken(new ReaderClient(applicationData.getProperty("reader.user.scope")));
        writerToken = AuthenticationClient.getToken(new WriterClient(applicationData.getProperty("writer.user.scope")));
        ApplicationClient.expandZipCodes(applicationData.getProperty("zip.codes"));
    }
    @BeforeEach
    public void init() {
        femaleUser = User.builder()
                .name(applicationData.getProperty("user.name"))
                .age(Integer.parseInt(applicationData.getProperty("user.age")))
                .sex(applicationData.getProperty("user.sex.female"))
                .build();
        maleUser = User.builder()
                .name(applicationData.getProperty("user.name"))
                .age(Integer.parseInt(applicationData.getProperty("user.age")))
                .sex(applicationData.getProperty("user.sex.male"))
                .build();
    }
}
