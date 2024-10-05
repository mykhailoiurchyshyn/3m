package m.api.tenant;

import api.controllers.TenantController;
import api.dtl.objects.Tenant;
import api.dtl.reponse.CreateTenantResponse;
import m.api.BaseMainAdminApiTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static m.data.TenantTestDataFactory.tenant;

public class TenantTest extends BaseMainAdminApiTest {
    private TenantController tenantController;

    @BeforeClass(alwaysRun = true)
    public void testSetUp() {
        tenantController = new TenantController(apiClient);
    }

    @Test(groups = {"API"})
    public void tenantCrudTest() {
        Tenant tenant = tenant();
        CreateTenantResponse response = tenantController.addTenant(tenant);
        String tenantId = response.getId();
        tenantController.deleteTenant(tenantId);
    }
}
