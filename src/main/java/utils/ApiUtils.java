package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import rest.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiUtils {
    @SneakyThrows
    public static List<String> getListFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, List.class);
    }

    @SneakyThrows
    public static List<User> getUserFromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue("[" + json + "]", new TypeReference<List<User>>() {
        });
    }

    @SneakyThrows
    public static String fromObjectToJson(Object object) {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }
    @SneakyThrows
    public static String fromResponseToString(CloseableHttpResponse response, int httpStatus) {
        assertThat(response.getStatusLine().getStatusCode(), equalTo(httpStatus));
        String jsonString;
        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
        return jsonString;
    }
}
