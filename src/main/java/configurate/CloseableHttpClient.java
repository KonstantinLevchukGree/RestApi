package configurate;

import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import rest.User;

import java.net.URI;

import static tests.BaseTest.readerToken;
import static tests.BaseTest.writerToken;

public class CloseableHttpClient {
    @SneakyThrows
    public static CloseableHttpResponse sendGet(String methodPoint) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", readerToken)
                .build();
        HttpGet httpget = new HttpGet(uri);
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpget);
    }

    @SneakyThrows
    public static CloseableHttpResponse sendGet(String methodPoint, User user) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", readerToken)
                .setParameter("olderThan", String.valueOf(user.getAge() - 1))
                .setParameter("sex", user.getSex())
                .build();
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("Content-type", "application/json");
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpget);
    }

    @SneakyThrows
    public static CloseableHttpResponse sendPost(String methodPoint, String json) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", writerToken)
                .build();
        HttpPost httpPost = new HttpPost(uri);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpPost);
    }
}
