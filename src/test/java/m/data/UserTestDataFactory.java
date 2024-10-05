package m.data;

import api.dtl.objects.User;

public class UserTestDataFactory {
    public static User user() {
        return User.builder().email("automation.admin@3motionai.com")
                .name("Automation").surname("Admin").role("3M Tech Admin")
                .roleNames(new String[] {"3M Tech Admin"}).organizationUnitIds(new String[]{}).build();
    }
}
