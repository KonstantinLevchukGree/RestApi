package tests;

import client.Client;
import org.junit.jupiter.api.BeforeEach;
import user.User;
import utils.authenticationClient.AuthenticationClient;
import utils.property.PropertyUtil;

import java.util.Properties;

public class BaseTest {
    public static String readerToken;
    public static String writerToken;
    public static User user;
    public static String incorrectZipCode;
    private static Client writerClient;
    private static Client readerClient;
    private  final Properties clientData = PropertyUtil.getProperties("clientData.properties");
    private  final Properties httpHost = PropertyUtil.getProperties("httpHost.properties");
    private  final Properties testData = PropertyUtil.getProperties("testData.properties");
    @BeforeEach
    public void init() {
        writerClient = new Client();
        writerClient.setUserName(clientData.getProperty("writer.user.name"));
        writerClient.setUserPassword(clientData.getProperty("writer.user.password"));
        writerClient.setGrantType(clientData.getProperty("writer.user.grant.type"));
        writerClient.setUserScope(clientData.getProperty("writer.user.scope"));

        readerClient = new Client();
        readerClient.setUserName(clientData.getProperty("reader.user.name"));
        readerClient.setUserPassword(clientData.getProperty("reader.user.password"));
        readerClient.setGrantType(clientData.getProperty("reader.user.grant.type"));
        readerClient.setUserScope(clientData.getProperty("reader.user.scope"));

        readerToken = AuthenticationClient.getToken(readerClient,httpHost.getProperty("port"),httpHost.getProperty("host.name"),httpHost.getProperty("scheme"));
        writerToken = AuthenticationClient.getToken(writerClient,httpHost.getProperty("port"),httpHost.getProperty("host.name"),httpHost.getProperty("scheme"));

        user=new User();
        user.setName(testData.getProperty("user.name"));
        user.setAge(Integer.parseInt(testData.getProperty("user.age")));
        user.setSex(testData.getProperty("user.sex"));

        incorrectZipCode=testData.getProperty("user.incorrect.zip.code");

    }
}
