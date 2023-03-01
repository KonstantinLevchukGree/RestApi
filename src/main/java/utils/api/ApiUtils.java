package utils.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import endPoint.Point;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import user.User;

import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiUtils {
    @SneakyThrows
    public static String sendGet(String token, String methodPoint, int statusCod) {
        HttpGet httpget = new HttpGet(createApiMethodUri(token, methodPoint));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(statusCod));
        String jsonString;
        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
        return jsonString;
    }

    @SneakyThrows
    public static String sendGet(String token, String methodPoint, int statusCod, User user) {
        HttpGet httpget = new HttpGet(createApiMethodUri(token, methodPoint, user));
        httpget.setHeader("Content-type", "application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(statusCod));
        String jsonString;
        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
        return jsonString;
    }

    @SneakyThrows
    public static String sendPost(String token, String methodPoint, String json, int statusCod) {
        HttpPost httpPost = new HttpPost(createApiMethodUri(token, methodPoint));
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode, equalTo(statusCod));
        String jsonString;
        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
        return jsonString;
    }

    @SneakyThrows
    public static List<String> getListFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, List.class);
    }

    @SneakyThrows
    public static List<User> getUserFromJsonString(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue("[" + json + "]", new TypeReference<List<User>>() {
        });
    }

    @SneakyThrows
    public static String getJsonStringFromObject(Object object) {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

    @SneakyThrows
    private static URI createApiMethodUri(String token, String methodPoint) {
        return new URIBuilder(new URI(Point.BASE_URL.getApiPoint() + methodPoint))
                .setParameter("access_token", token)
                .build();
    }

    @SneakyThrows
    private static URI createApiMethodUri(String token, String methodPoint, User user) {
        return new URIBuilder(new URI(Point.BASE_URL.getApiPoint() + methodPoint))
                .setParameter("access_token", token)
                .setParameter("olderThan", String.valueOf(user.getAge() - 1))
                .setParameter("sex", user.getSex())
                .build();
    }
}
