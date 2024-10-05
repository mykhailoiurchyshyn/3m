package m.api;

import api.UserRole;
import api.client.ApiClient;
import api.client.IApiClient;
import api.client.filters.LogbackRequestLoggingFilter;
import api.client.filters.LogbackResponseLoggingFilter;
import api.controllers.ApiClientFactory;
import api.controllers.UserController;
import api.dtl.reponse.AuthenticationResponse;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.*;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.*;
import io.restassured.specification.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import m.BaseTest;

import java.util.*;

import static io.restassured.RestAssured.given;
import static utils.PropertiesHandler.getPropertyValue;

@Slf4j
public abstract class BaseApiTest extends BaseTest {
    protected ApiClientFactory apiClientFactory;
    private String baseAuthUrl = getPropertyValue(env, "domain_auth_server") + "/connect/token";

    public abstract void testSetUp();

    @SneakyThrows
    public IApiClient authentication(UserRole userRole, String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", getPropertyValue(env, "grant_type"));
        params.put("scope", getPropertyValue(env, "scope"));
        params.put("client_id", getPropertyValue(env, "client_id"));
        params.put("client_secret", getPropertyValue(env, "client_secret"));
        params.put("username", username);
        params.put("password", password);

        RestAssured.config = RestAssured.config().sslConfig(
                new SSLConfig().allowAllHostnames().relaxedHTTPSValidation());

        RequestSpecification authenticationRequestSpecification = given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType(ContentType.URLENC).params(params);
        if (userRole != UserRole.MAIN_ADMIN) {
            authenticationRequestSpecification.header("__tenant", getPropertyValue(env, "atTenant"));
        }

        AuthenticationResponse authenticationResponse = authenticationRequestSpecification
                .post(baseAuthUrl)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);
//
//        RequestSpecification authenticationRequestSpecification = new RequestSpecBuilder()
//                .setContentType(ContentType.URLENC).addParams(params)
//                .addFilters(Arrays.asList(
//                        new LogbackRequestLoggingFilter(log),
//                        new LogbackResponseLoggingFilter(log)
//                ))
//                .build();
//
//        AuthenticationResponse authenticationResponse = given().spec(authenticationRequestSpecification)
//                .post(baseAuthUrl)
//                .then()
//                .statusCode(200)
//                .extract().as(AuthenticationResponse.class);

        String authToken = authenticationResponse.getAccessToken();
        apiClientFactory = new ApiClientFactory(env, authToken);

        log("User is Authenticated Successfully");

        return apiClientFactory.getApiClient(userRole, authToken);
    }
}
