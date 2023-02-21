package utils.api;

import endPoint.Point;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class ApiUtils {

    private static BasicCredentialsProvider authorizationUser(String userName, String passwordUser){
        HttpHost targetHost = new HttpHost("http", "localhost", 9090);
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        provider.setCredentials(authScope, new UsernamePasswordCredentials
                (userName, passwordUser.toCharArray()));
        return provider;
    }

    private static List<NameValuePair> putParametersPost(String grantTypeName,String scopeName){
        List<NameValuePair> nvp = new ArrayList<>();
        nvp.add(new BasicNameValuePair("grant_type", grantTypeName));
        nvp.add(new BasicNameValuePair("scope", scopeName));
        return nvp;
    }

    /*private static CloseableHttpClient getHttpClient(){

    }*/
    @SneakyThrows
public static CloseableHttpResponse sendGet(User user, String a, String endPoint){

        HttpPost httpPost = new HttpPost(Point.BASE_URL.getApiMethod()+a);
        httpPost.setEntity(new UrlEncodedFormEntity(putParametersPost(user.getGrantType(), user.getUserScope())));
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(authorizationUser(user.getUserName(), user.getUserPassword()))
                .build();
        client.execute(httpPost);


        HttpGet httpGet = new HttpGet(Point.BASE_URL.getApiMethod()+endPoint);
        client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpGet);
        return response;
    }
    @SneakyThrows
    public static CloseableHttpResponse sendPost(User user, String endPoint){
        HttpPost httpPost = new HttpPost(Point.BASE_URL.getApiMethod()+endPoint);
        httpPost.setEntity(new UrlEncodedFormEntity(putParametersPost(user.getGrantType(), user.getUserScope())));
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(authorizationUser(user.getUserName(), user.getUserPassword()))
                .build();
        return client.execute(httpPost);
        /*String result;

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            System.out.println(response.getVersion()); // HTTP/1.1
            System.out.println(response.getCode()); // 200
            System.out.println(response.getReasonPhrase()); // OK

            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println(result);
            // Ensure that the stream is fully consumed
            EntityUtils.consume(entity);
        }
        return result;*/
    }
}
