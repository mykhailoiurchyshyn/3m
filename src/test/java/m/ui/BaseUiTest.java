package m.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import m.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.codeborne.selenide.testng.TextReport;
import ui.pages.LoginPage;
import utils.AllureLogger;

import java.io.ByteArrayInputStream;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.PropertiesHandler.getPropertyValue;

@Slf4j
@Listeners({TextReport.class})
public class BaseUiTest extends BaseTest {
    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        log("Opening browser");
        Selenide.open(getPropertyValue(env, "frontend_url"));
        getWebDriver().manage().window().maximize();
        loginPage = new LoginPage();
    }

    @AfterMethod(alwaysRun = true)
    public void logsAndScreenshots(ITestResult result) {
//        SelenideLogger
        testExecutionLog(result);
//        AllureLogger.attachExecutionLogs()
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot(result);

            LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get(LogType.BROWSER);
            attachBrowserLogs(logs);
        }
        closeWebDriver();
    }

//    public void configureChromeDriver() {
//        Configuration.browser = "chrome";
//        Configuration.browserVersion = "latest";
//    }

    public void configureChromeDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Attachment(value = "Browser logs", type = "text/plain")
    private void attachBrowserLogs(LogEntries logs) {
        if (logs == null || logs.getAll().isEmpty()) {
            log.info("No browser logs found");
            return;
        }
        StringBuilder logText = new StringBuilder();
        for (LogEntry logEntry : logs) {
            logText.append(logEntry.getLevel()).append(" ").append(logEntry.getMessage()).append("\n");
        }
        Allure.addAttachment("Browser Logs", logText.toString());
    }

//    @Attachment(value = "Screenshot >> {0}", type = "image/png")
    private static void attachScreenshot(ITestResult result) {
//        log.info(String.format("Attach screenshot for %s_%s", result.getTestClass().getName(), result.getMethod().getMethodName()));
        try {
            byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception exception) {
            log.info(String.format("Failed to attach screenshot for %s_%s", result.getTestClass().getName(), result.getMethod().getMethodName()));
        }
    }

    @Attachment(value = "Execution Logs", type = "text/plain")
    public void attachExecutionLogs(String logs) {
        Allure.addAttachment("Execution Logs", logs);
    }
}
