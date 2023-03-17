package rest;

import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class Response {
    @SneakyThrows
    public static String closeResponse(CloseableHttpResponse response) {
        String jsonString;
        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
        return jsonString;
    }
}
