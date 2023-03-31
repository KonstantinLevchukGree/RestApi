package configurate;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import rest.HttpDeleteWithBody;

import java.io.File;
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
    public static CloseableHttpResponse sendGet(String methodPoint, String parameter, int age) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", readerToken)
                .setParameter(parameter, String.valueOf(age))
                .build();
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("Content-type", "application/json");
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpget);
    }

    @SneakyThrows
    public static CloseableHttpResponse sendGet(String methodPoint, String parameter, String sex) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", readerToken)
                .setParameter(parameter, sex)
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

    @SneakyThrows
    public static CloseableHttpResponse sendPost(String methodPoint, File file) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", writerToken)
                .build();
        HttpPost httpPost = new HttpPost(uri);
        FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("file", fileBody);
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClientBuilder.create()
                .build();
        return httpclient.execute(httpPost);
    }

    @SneakyThrows
    public static CloseableHttpResponse sendPut(String methodPoint, String json) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", writerToken)
                .build();
        HttpPut httpPut = new HttpPut(uri);
        StringEntity entity = new StringEntity(json);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setEntity(entity);
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpPut);
    }

    @SneakyThrows
    public static CloseableHttpResponse sendDelete(String methodPoint, String json) {
        URI uri = new URIBuilder(new URI(methodPoint))
                .setParameter("access_token", writerToken)
                .build();
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(uri);
        StringEntity entity = new StringEntity(json);
        httpDelete.setEntity(entity);
        httpDelete.setHeader("Content-type", "application/json");
        org.apache.http.impl.client.CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpDelete);
    }
}
