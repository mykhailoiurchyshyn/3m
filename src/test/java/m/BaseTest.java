package m;

import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Environment;
import utils.PropertiesHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

@Slf4j
public class BaseTest {
    public final Environment env = getEnv();

    public void log(String message) {
        log.info(message);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        MDC.remove("mdcTestName");
        String testName = String.format("%s_%s", method.getDeclaringClass().getName(), method.getName());
        MDC.put("mdcTestName", testName);
        log.info(String.format("Integration Test: [%s] STARTED", testName));
        log.info("-------------------------------------------");

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .enableLogs(LogType.DRIVER, Level.ALL)
                .enableLogs(LogType.CLIENT, Level.ALL)
//                .enableLogs(LogType.SERVER, Level.ALL)
                .enableLogs(LogType.BROWSER, Level.ALL)
        );

    }

    @AfterMethod(alwaysRun = true)
    @Attachment(value = "Logs", type = "text/plain")
    public byte[] testExecutionLog(ITestResult result) {
        try {
            return Files.readAllBytes(Paths.get(String.format("%s/target/log/%s_%s.txt", System.getProperty("user.dir"), result.getTestClass().getName(), result.getMethod().getMethodName())));
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to attach logs".getBytes();
        }
    }

    private Environment getEnv() {
        String env = System.getProperty("env");
        if (System.getProperty("env") == null) {
            String filePath = String.format("%s/src/test/resources/config.properties", System.getProperty("user.dir"));
            env = PropertiesHandler.readPropertyValue(filePath, "env");
        }
        return Environment.valueOf(env);
    }
}


// 1. Finish with logs
// 2. Finish with db -> dto
// 3. Jenkins
// 4. Де ранити і як ранити - Selenoid, Kubernetes, etc
