package tests;

import endPoint.Point;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import utils.api.ApiUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZipCodeTest extends BaseTest{
    @Test public void ddd(){
       // CloseableHttpResponse response2 = ApiUtils.sendGet(readerUser, Point.GET_USER_TOKEN.getApiMethod(),Point.GET_ZIP_CODES.getApiMethod());
      //  assertThat(response2.getCode(), equalTo(HttpStatus.SC_OK));
    }
}
