package rest.factory.client;

import lombok.Getter;

public class WriterClient extends Client {
    @Getter
    private String userScope;

    public WriterClient(String userScope) {
        this.userScope = userScope;
    }
}
