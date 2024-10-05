package utils;

import io.qameta.allure.Attachment;

public class AllureLogger {

    @Attachment(value = "Execution Logs", type = "text/plain")
    public static String attachExecutionLogs(String logs) {
        return logs;
    }
}

