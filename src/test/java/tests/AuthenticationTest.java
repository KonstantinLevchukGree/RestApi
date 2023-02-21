package tests;

import endPoint.Point;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utils.api.ApiUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthenticationTest extends BaseTest{

    @Test
    public void verifyAuthenticationWriterUser() {
        CloseableHttpResponse response = ApiUtils.sendPost(writerUser, Point.GET_USER_TOKEN.getApiMethod());
        assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void verifyAuthenticationReaderUser() {
        CloseableHttpResponse response = ApiUtils.sendPost(readerUser, Point.GET_USER_TOKEN.getApiMethod());
        assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
    }
}
