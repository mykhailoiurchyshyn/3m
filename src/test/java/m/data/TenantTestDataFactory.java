package m.data;

import api.dtl.objects.Tenant;

public class TenantTestDataFactory {
    public static Tenant tenant() {
        return Tenant.builder().name("Test Tenant").adminEmailAddress("test.automation.admin@3motionai.com")
                .adminPassword("QWEasd12!!").build();
    }
}
