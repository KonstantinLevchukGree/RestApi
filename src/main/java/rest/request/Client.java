package rest.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Client {
    private String userName;
    private String userPassword;
    private String grantType;
    private String userScope;
}
