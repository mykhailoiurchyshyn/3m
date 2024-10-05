package ui.pages;

import lombok.SneakyThrows;
import ui.BasePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HomePage extends BasePage {
    public void validatePage() {
        log("Asserting no error");
        assertThat("No error", true, is(true));
    }

    @SneakyThrows
    public void validatePage2() {
        log("Asserting the error");
        assertThat("Just error", false, is(true));
    }
}
