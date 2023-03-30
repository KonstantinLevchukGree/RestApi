package utils;

import rest.request.Client;

import static tests.BaseTest.applicationData;

public class SingletonWriterClient {
    private static Client writerClient;

    private SingletonWriterClient() {
    }

    public static Client getClient() {
        if (writerClient == null) {
            writerClient = Client.builder()
                    .userName(applicationData.getProperty("writer.user.name"))
                    .userPassword(applicationData.getProperty("writer.user.password"))
                    .grantType(applicationData.getProperty("writer.user.grant.type"))
                    .userScope(applicationData.getProperty("writer.user.scope"))
                    .build();
        }
        return writerClient;
    }
}
