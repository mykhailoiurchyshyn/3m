package api.controllers;

import api.client.MainAdminApiClient;
import api.dtl.objects.User;
import api.dtl.reponse.CreateTenantResponse;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserController {
    private final MainAdminApiClient apiClient;
    protected String baseUrl;
    protected String baseCustomUrl;

    public UserController(MainAdminApiClient apiClient) {
        this.apiClient = apiClient;
        baseUrl = apiClient.getBaseUrl() + "/api/identity/users";
        baseCustomUrl = baseUrl + "/custom";
    }

    @Step
    public CreateTenantResponse addUser(User user) {
        log.info("Add user");
        return apiClient.post(baseCustomUrl, user, CreateTenantResponse.class, 200);
    }

    public void getUser() {

    }

    @Step
    public void deleteUser(String userId) {
        log.info("Delete user");
        apiClient.delete(String.format("%s/%s", baseUrl, userId), 204);
    }
}
