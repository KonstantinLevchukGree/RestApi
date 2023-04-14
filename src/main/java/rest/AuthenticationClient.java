package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import rest.factory.client.Client;
import rest.response.Token;

import java.util.ArrayList;
import java.util.List;

import static tests.httpClient.BaseTest.appData;

public class AuthenticationClient {
    private static BasicCredentialsProvider getProvider(String userName, String passwordUser) {
        HttpHost targetHost = new HttpHost(appData.getProperty("token.host.name")
                , Integer.parseInt(appData.getProperty("token.port.name"))
                , appData.getProperty("token.scheme"));
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        provider.setCredentials(authScope, new UsernamePasswordCredentials
                (userName, passwordUser));
        return provider;
    }

    @SneakyThrows
    private static CloseableHttpClient getClient(HttpPost httpPost, Client user) {
        List<NameValuePair> nvp = new ArrayList<>();
        nvp.add(new BasicNameValuePair("grant_type", user.getGrantType()));
        nvp.add(new BasicNameValuePair("scope", user.getUserScope()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvp));
        return HttpClientBuilder.create()
                .setDefaultCredentialsProvider(getProvider(user.getUserName(), user.getUserPassword()))
                .build();
    }

    @SneakyThrows
    private static CloseableHttpResponse getUser(Client user) {
        HttpPost httpPost = new HttpPost(appData.getProperty("api.user.token"));
        CloseableHttpClient client = getClient(httpPost, user);
        return client.execute(httpPost);
    }

    @SneakyThrows
    public static String getToken(Client user) {
        CloseableHttpResponse response = getUser(user);
        String jsonString;
        try {
            HttpEntity entity = response.getEntity();
            jsonString = EntityUtils.toString(entity);
        } finally {
            response.close();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = objectMapper.readValue(jsonString, Token.class);
        return token.getAccess_token();
    }
}