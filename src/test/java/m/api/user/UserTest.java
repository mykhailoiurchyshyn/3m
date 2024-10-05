package m.api.user;

import api.controllers.UserController;
import api.dtl.objects.User;
import api.dtl.reponse.CreateTenantResponse;
import m.api.BaseMainAdminApiTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static m.data.UserTestDataFactory.user;


public class UserTest extends BaseMainAdminApiTest {
    private UserController userController;

    @BeforeClass(alwaysRun = true)
    public void testSetUp() {
        userController = new UserController(apiClient);
    }

    @Test(groups = {"API"})
    public void userCrudTest() {
        User user = user();
        CreateTenantResponse response = userController.addUser(user);
        String userId = response.getId();
        userController.deleteUser(userId);
    }
}
