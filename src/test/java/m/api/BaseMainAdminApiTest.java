package m.api;

import api.UserRole;
import api.client.MainAdminApiClient;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeClass;

import static utils.PropertiesHandler.getPropertyValue;

public abstract class BaseMainAdminApiTest extends BaseApiTest {
    protected MainAdminApiClient apiClient;

    @SneakyThrows
    @Step
    @BeforeClass(alwaysRun = true)
    public void authenticateMainAdmin() {
        log("Authenticate Main Admin");
        apiClient = (MainAdminApiClient) authentication(UserRole.MAIN_ADMIN, getPropertyValue(env, "super_admin_username"), getPropertyValue(env, "super_admin_password"));
    }
}
