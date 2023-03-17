package utils.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import objects.User;

import java.util.List;

public class ApiUtils {
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
}
