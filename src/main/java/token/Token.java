package token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    public String access_token;

    public String getAccess_token() {
        return access_token;
    }
}
