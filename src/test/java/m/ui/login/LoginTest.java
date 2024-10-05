package m.ui.login;

import m.ui.BaseUiTest;
import org.testng.annotations.Test;

import static ui.pages.LoginPage.invalidLogin;
import static ui.pages.LoginPage.login;
import static utils.PropertiesHandler.getPropertyValue;

public class LoginTest extends BaseUiTest {
    @Test(groups = {"UI"})
    public void login11Test() {
        log("starting test for tenant admin");
        login(getPropertyValue(env, "tenant_admin_email"), getPropertyValue(env, "tenant_admin_password"))
                .validatePage();
    }

    @Test(groups = {"UI"})
    public void login12Test() {
        log("starting test for tenant member");
        invalidLogin(getPropertyValue(env, "tenant_member_email"), getPropertyValue(env, "tenant_admin_password"))
                .validatePage2();
    }
}
