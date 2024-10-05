package api.controllers;

import api.UserRole;
import api.client.IApiClient;
import api.client.MainAdminApiClient;
import api.dtl.reponse.AuthenticationResponse;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseController {
    protected IApiClient apiClient;
    protected String baseUrl = "https://api-dev.3motionai.com/";
    private String baseAuthUrl = "https://auth-dev.3motionai.com/connect/token";

    /**
     * Authenticate user
     * @return - authToken
     */
    protected String authenticate(UserRole userRole, String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("scope", "offline_access _3M");
        params.put("client_id", "_3M_Postman_Internal");
        params.put("client_secret", "1q2w3e*");
        params.put("username", username);
        params.put("password", password);

        RestAssured.config = RestAssured.config().sslConfig(
                new SSLConfig().allowAllHostnames().relaxedHTTPSValidation());

        RequestSpecification authenticationRequestSpecification = given()
                .filter(new RequestLoggingFilter())
                .contentType(ContentType.URLENC).params(params);
        if (userRole != UserRole.MAIN_ADMIN) {
            authenticationRequestSpecification.header("__tenant", "3a1177e1-9a29-f92a-3601-287f0a746d8b");
        }

        AuthenticationResponse authenticationResponse = authenticationRequestSpecification
                .post(baseAuthUrl)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);

        return authenticationResponse.getAccessToken();
    }
}
