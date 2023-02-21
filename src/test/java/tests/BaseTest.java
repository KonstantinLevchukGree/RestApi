package tests;

import org.junit.jupiter.api.BeforeEach;
import user.User;
import utils.property.PropertyUtil;

import java.util.Properties;

public class BaseTest {
    public static User writerUser;
    public static User readerUser;
    private final Properties userData = PropertyUtil.getProperties("userData.properties");

    @BeforeEach
    public void initUser() {
        writerUser = new User();
        writerUser.setUserName(userData.getProperty("writer.user.name"));
        writerUser.setUserPassword(userData.getProperty("writer.user.password"));
        writerUser.setGrantType(userData.getProperty("writer.user.grant.type"));
        writerUser.setUserScope(userData.getProperty("writer.user.scope"));

        readerUser = new User();
        readerUser.setUserName(userData.getProperty("reader.user.name"));
        readerUser.setUserPassword(userData.getProperty("reader.user.password"));
        readerUser.setGrantType(userData.getProperty("reader.user.grant.type"));
        readerUser.setUserScope(userData.getProperty("reader.user.scope"));
    }
}
