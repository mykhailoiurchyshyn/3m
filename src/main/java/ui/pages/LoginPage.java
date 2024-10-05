    package ui.pages;

    import com.codeborne.selenide.SetValueOptions;
    import io.qameta.allure.Step;
    import lombok.extern.slf4j.Slf4j;
    import org.apache.commons.lang3.ObjectUtils;
    import org.openqa.selenium.By;
    import ui.BasePage;

    import static com.codeborne.selenide.Condition.visible;
    import static com.codeborne.selenide.Selenide.$;
    import static com.codeborne.selenide.Selenide.$$;
    import static com.codeborne.selenide.SetValueOptions.withText;

    @Slf4j
    public class LoginPage extends BasePage {
        private static final By EMAIL_INPUT = By.id("email");
        private static final By PASSWORD_INPUT = By.id("password");
        private static final By LOGIN_BUTTON = By.id("loginBtn");
        private static final By INVALID_LOGIN_BUTTON = By.id("login");

        @Step("Login")
        public static HomePage login(String email, String password) {
            $(EMAIL_INPUT).setValue(email);
            $(PASSWORD_INPUT).setValue(withText(password).sensitive());
            $(LOGIN_BUTTON).click();
            return new HomePage();
        }

        @Step
        public static HomePage invalidLogin(String email, String password) {
            $(EMAIL_INPUT).setValue(email);
            $(PASSWORD_INPUT).setValue(withText(password).sensitive());
            $(INVALID_LOGIN_BUTTON).click();
            return new HomePage();
        }

        public LoginPage testM(BasePage a) throws RuntimeException {
            return this;
        }
    }
