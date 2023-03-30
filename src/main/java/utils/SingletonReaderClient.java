package utils;

import rest.request.Client;

import static tests.BaseTest.applicationData;

public class SingletonReaderClient {
    private static Client readerClient;

    private SingletonReaderClient() {
    }

    public static Client getClient() {
        if (readerClient == null) {
            readerClient = Client.builder()
                    .userName(applicationData.getProperty("reader.user.name"))
                    .userPassword(applicationData.getProperty("reader.user.password"))
                    .grantType(applicationData.getProperty("reader.user.grant.type"))
                    .userScope(applicationData.getProperty("reader.user.scope"))
                    .build();
        }
        return readerClient;
    }
}

