package api.controllers;

import api.client.MainAdminApiClient;
import api.dtl.objects.Tenant;
import api.dtl.reponse.CreateTenantResponse;
import io.qameta.allure.Step;

public class TenantController {
    private final MainAdminApiClient apiClient;
    protected String baseUrl;

    public TenantController(MainAdminApiClient apiClient) {
        this.apiClient = apiClient;
        baseUrl = apiClient.getBaseUrl() + "/api/saas/tenants";
    }
//    public TenantController() {
//        apiClient = new MainAdminApiClient(authenticate(UserRole.MAIN_ADMIN, "admin@abp.io", "1q2w3E*"));
//        this.baseUrl = super.baseUrl + "api/saas/tenants";
//    }

    @Step
    public CreateTenantResponse addTenant(Tenant tenant) {
        return apiClient.post(baseUrl, tenant, CreateTenantResponse.class, 200);
    }

    public void getTenant() {

    }

    @Step
    public void deleteTenant(String tenantId) {
        apiClient.delete(String.format("%s/%s", baseUrl, tenantId), 204);
    }
}
