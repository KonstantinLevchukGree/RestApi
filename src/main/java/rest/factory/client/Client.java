package rest.factory.client;

import lombok.Getter;

import static tests.httpClient.BaseTest.appData;

@Getter
public abstract class Client {
    private String userName;
    private String userPassword;
    private String grantType;

    public Client() {
        this.userName = appData.getProperty("reader.user.name");
        this.userPassword = appData.getProperty("reader.user.password");
        this.grantType = appData.getProperty("reader.user.grant.type");
    }

    public abstract String getUserScope();
}
