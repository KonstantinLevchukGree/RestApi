package configurate;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.io.File;

import static io.restassured.RestAssured.given;
import static tests.httpClient.BaseTest.readerToken;
import static tests.httpClient.BaseTest.writerToken;

public class RestAssuredClient {
    private static final Logger log = Logger.getLogger(RestAssuredClient.class);

    public static Response sendGet(String methodPoint) {
        log.info("GET  " + methodPoint);
        Response response = given().auth()
                .oauth2(readerToken)
                .when()
                .get(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }

    public static Response sendGet(String methodPoint, String parameter, String sex) {
        log.info("GET  " + methodPoint);
        Response response = given().auth()
                .oauth2(readerToken)
                .when()
                .queryParam(parameter, sex)
                .get(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }

    public static Response sendGet(String methodPoint, String parameter, int age) {
        log.info("GET  " + methodPoint);
        Response response = given().auth()
                .oauth2(readerToken)
                .when()
                .queryParam(parameter, String.valueOf(age))
                .get(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }

    public static Response sendPost(String methodPoint, String json) {
        log.info("POST  " + methodPoint);
        Response response = given().auth()
                .oauth2(writerToken)
                .when()
                .contentType(ContentType.JSON)
                .body(json)
                .post(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }

    public static Response sendPost(String methodPoint, File file) {
        log.info("POST  " + methodPoint);
        Response response = given().auth()
                .oauth2(writerToken)
                .contentType(ContentType.MULTIPART)
                .multiPart(file)
                .post(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }

    public static Response sendDelete(String methodPoint, String json) {
        log.info("DELETE  " + methodPoint);
        Response response = given().auth()
                .oauth2(writerToken)
                .when()
                .contentType(ContentType.JSON)
                .body(json)
                .delete(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }

    public static Response sendPut(String methodPoint, String json) {
        log.info("PUT  " + methodPoint);
        Response response = given().auth()
                .oauth2(writerToken)
                .when()
                .contentType(ContentType.JSON)
                .body(json)
                .put(methodPoint)
                .then()
                .extract()
                .response();
        log.info(response.getBody().asString());
        return response;
    }
}
