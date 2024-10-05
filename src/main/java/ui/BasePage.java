package ui;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class BasePage {
//    protected Logger log = LoggerFactory.getLogger(BasePage.class);

    public void log(String message) {
        log.info(message);
    }

    public BasePage testM(BasePage a) throws NullPointerException {
        return this;
    }
}
