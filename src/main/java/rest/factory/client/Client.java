package rest.factory.client;

import lombok.Getter;

import static tests.BaseTest.applicationData;

@Getter
public abstract class Client {
    private String userName;
    private String userPassword;
    private String grantType;

    public Client() {
        this.userName = applicationData.getProperty("reader.user.name");
        this.userPassword = applicationData.getProperty("reader.user.password");
        this.grantType = applicationData.getProperty("reader.user.grant.type");
    }

    public abstract String getUserScope();
}
