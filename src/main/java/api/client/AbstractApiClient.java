package api.client;

import api.client.filters.LogbackRequestLoggingFilter;
import api.client.filters.LogbackResponseLoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import utils.Environment;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.PropertiesHandler.getPropertyValue;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractApiClient implements IApiClient {
    private String authToken;
    private String baseUrl;

    public AbstractApiClient(Environment env, String authToken) {
        this.authToken = authToken;
        this.baseUrl = getPropertyValue(env, "domain_api");
    }

    public <T> T get(String url, Class<T> responseType, int statusCode) {
        return given().spec(getRequestSpecification(url))
                .get().then().statusCode(statusCode).extract().as(responseType);
    }

    public <T> T post(String url, Map<String, Object> parameters, Class<T> responseType, int statusCode) {
        return  given().spec(requestSpecification(url)).body(parameters)
                .when().post()
                .then()
                .statusCode(statusCode)
                .extract().as(responseType);
    }

//    public <T> T post(String url, T requestType, Class<T> responseType) {
//        return given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
//                .spec(requestSpecification(url)).body(requestType)
//                .when().post()
//                .then().log().status()
//                .extract().as(responseType);
//    }

    public <RQ, RS> RS post(String url, RQ requestType, Class<RS> responseType, int statusCode) {
        return given().spec(requestSpecification(url))
                .body(requestType)
                .post()
                .then()
                .statusCode(statusCode)
                .extract().as(responseType);
    }

    public void delete(String url, int statusCode) {
         given().spec(requestSpecification(url))
                 .delete(url)
                 .then()
                 .statusCode(statusCode);
    }

    /**
     * Basic method for getting post/put/patch request specification.
     * @return {@code RequestSpecification} - request specification object
     */
    protected RequestSpecification requestSpecification(String url) {
        return requestSpecification(url, authToken);
    }

    protected RequestSpecification requestSpecification(String url, String authToken) {
        return requestSpecification(url, ContentType.JSON, authToken);
    }

    protected RequestSpecification requestSpecification(String url, ContentType contentType, String authToken) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(contentType)
                .addHeader("Authorization", "Bearer " + authToken)
                .addFilters(Arrays.asList(
                        new LogbackRequestLoggingFilter(log),
                        new LogbackResponseLoggingFilter(log)
                ))
                .build().filter(new AllureRestAssured());
    }

    protected RequestSpecification getRequestSpecification(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .addHeader("Authorization", "Bearer token=" + authToken)
                .addFilters(Arrays.asList(
                        new LogbackRequestLoggingFilter(log),
                        new LogbackResponseLoggingFilter(log)
                ))
                .build().filter(new AllureRestAssured());
    }

    protected RequestSpecification getRequestSpecification(String url, String authToken) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .addHeader("Authorization", "Bearer token=" + authToken)
                .addFilters(Arrays.asList(
                        new LogbackRequestLoggingFilter(log),
                        new LogbackResponseLoggingFilter(log)
                ))
                .build().filter(new AllureRestAssured());
    }
}
