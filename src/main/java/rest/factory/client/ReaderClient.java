package rest.factory.client;

import lombok.Getter;

public class ReaderClient extends Client {
    @Getter
    private String userScope;

    public ReaderClient(String userScope) {
        this.userScope = userScope;
    }

}
