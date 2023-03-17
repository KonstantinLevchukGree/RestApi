package rest;

import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import objects.User;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static tests.BaseTest.readerToken;
import static tests.BaseTest.writerToken;

public class Request {
    @SneakyThrows
    public static CloseableHttpResponse sendGet(String methodPoint) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", readerToken)
                .build();
        HttpGet httpget = new HttpGet(uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        return response;
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
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));
        return response;
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
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        return response;
    }
}
