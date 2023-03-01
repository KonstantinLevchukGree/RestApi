package client;

public class Client {
    private String userName;
    private String userPassword;
    private String grantType;
    private String userScope;

    public Client() {
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getUserScope() {
        return userScope;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setUserScope(String userScope) {
        this.userScope = userScope;
    }
}
