package utils.authenticationClient;

import client.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import endPoint.Point;
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
import token.Token;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationClient {
    private static BasicCredentialsProvider getProvider(String userName, String passwordUser, String port, String hostName, String scheme) {
        HttpHost targetHost = new HttpHost(hostName, Integer.parseInt(port), scheme);
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        provider.setCredentials(authScope, new UsernamePasswordCredentials
                (userName, passwordUser));
        return provider;
    }

    @SneakyThrows
    private static CloseableHttpClient getClient(HttpPost httpPost, Client user, String port, String hostName, String scheme) {
        httpPost.setEntity(new UrlEncodedFormEntity(putParametersPost(user.getGrantType(), user.getUserScope())));
        return HttpClientBuilder.create()
                .setDefaultCredentialsProvider(getProvider(user.getUserName(), user.getUserPassword(), port, hostName, scheme))
                .build();
    }

    private static HttpPost getHttpPost() {
        return new HttpPost(Point.BASE_URL.getApiPoint() + Point.USER_TOKEN.getApiPoint());
    }

    private static List<NameValuePair> putParametersPost(String grantTypeName, String scopeName) {
        List<NameValuePair> nvp = new ArrayList<>();
        nvp.add(new BasicNameValuePair("grant_type", grantTypeName));
        nvp.add(new BasicNameValuePair("scope", scopeName));
        return nvp;
    }

    @SneakyThrows
    private static CloseableHttpResponse getUser(Client user, String port, String hostName, String scheme) {
        HttpPost httpPost = getHttpPost();
        CloseableHttpClient client = getClient(httpPost, user, port, hostName, scheme);
        return client.execute(httpPost);
    }

    @SneakyThrows
    public static String getToken(Client user, String port, String hostName, String scheme) {
        CloseableHttpResponse response = getUser(user, port, hostName, scheme);
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